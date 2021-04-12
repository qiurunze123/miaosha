package com.geekq.miaosha.service;

import com.geekq.miaosha.biz.entity.MiaoshaOrder;
import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.biz.entity.OrderInfo;
import com.geekq.miaosha.mq.MQServiceFactory;
import com.geekq.miaosha.mq.MiaoShaMessage;
import com.geekq.miaosha.redis.*;
import com.geekq.miaosha.redis.redismanager.RedisLimitRateWithLUA;
import com.geekq.miaosha.util.StringBeanUtil;
import com.geekq.miaosha.utils.MD5Utils;
import com.geekq.miaosha.utils.UUIDUtil;
import com.geekq.miaosha.vo.GoodsExtVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Random;

import static com.geekq.miaosha.enums.enums.ResultStatus.*;

@Service
public class MiaoShaComposeService {
	
	@Autowired
	GoodsComposeService goodsComposeService;
	@Autowired
	OrderComposeService orderComposeService;
	@Autowired
	RedisService redisService;

	private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

/*真正扣减库存，并生成订单
*
* */
	public OrderInfo miaoSha(MiaoshaUser user, GoodsExtVo goodsVo, int expireTime){
		OrderInfo orderInfo=new OrderInfo();
		boolean success=false;
		success = goodsComposeService.reduceStock(goodsVo);
		if(success){
			orderInfo= orderComposeService.createOrderInfoAndMIaoShaOrder(user,goodsVo,expireTime);
		}else{

			/*要对失败原因进行具体分析，不一定是因为库存不足导致的失败*/

			//redisService.incr(GoodsKey.getMiaoshaGoodsStock, "" + goodsVo.getId());
			setGoodsOver(goodsVo.getId());
		}
		return orderInfo;
	}

	/*取消订单时，要注意数据库和缓存的数据最终一致性
	 *
	 * */
	@Transactional
	public boolean cancelOrder(OrderInfo orderInfo) {
		boolean deleteOrder=true;
		GoodsExtVo goods=new GoodsExtVo();
		goods.setId(orderInfo.getGoodsId());
		goods.setGoodsStock(orderInfo.getGoodsCount());
		//最好使用分布式锁,对资源的竞争修改导致数据不正确
		//这里应该保证原子性
		synchronized (this){
			boolean addSuccessOrNot=goodsComposeService.addStock(goods);
			if(addSuccessOrNot){
				redisService.incr(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId());
				deleteOrder= orderComposeService.deleteOrder(orderInfo.getId());
			}
		}


		return deleteOrder;
	}

	    //生成秒杀订单信息
        /*如果秒杀商品库存较少时，可以直接访问数据库
        如果秒杀商品库存量大是，就需要使用消息对列队请求进行削峰
        *
        * */
	public OrderInfo doMiaoSha(MiaoshaUser user, Long goodsId, boolean syncOrder){
		OrderInfo orderInfo=new OrderInfo();
		int expireTime=30;
		if(syncOrder){//同步订单
			orderInfo=this.syncMiaoSha(user,goodsId,expireTime);
		}else{//订单不同步,异步请求
            orderInfo=this.asyncMiaoSha(user,goodsId);
		}
		return orderInfo;
	}


	public String checkMiaoSha(MiaoshaUser user,  String path, long goodsId){
		String checkResult="success";

		if (user == null) {
			checkResult=SESSION_ERROR.getMessage();
			return checkResult;
		}

		/**
		 * 分布式限流
		 * 可能会违背秒杀请求先到先得的特性
		 */
		try {
			RedisLimitRateWithLUA.accquire();
		} catch (IOException e) {
			checkResult=REPEATE_MIAOSHA.getMessage();
			return checkResult;
		} catch (URISyntaxException e) {
			checkResult= REPEATE_MIAOSHA.getMessage();
			return checkResult;
		}

		/*
		 * 校验秒杀url地址，防止刷流量
		 * */
		String pathValue=redisService.get(MiaoshaKey.getMiaoshaPath, ""+user.getNickname() + "_"+ goodsId,String.class);
		if(! pathValue.equals(path)){
			return checkResult;
		}
		/*判断是否重复秒杀
		 *
		 * */
		MiaoshaOrder miaoshaOrder= redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+user.getId()+"_"+goodsId,MiaoshaOrder.class) ;;
		if(null !=miaoshaOrder){
			checkResult= REPEATE_MIAOSHA.getMessage();
			return checkResult;
		}

		//内存标记，减少redis访问，没有将库存查询写入redis
		//写内存的方法不妥当，不适于分布式部署，故取消内存访问
		/*boolean over = localOverMap.get(goodsId);
		if (over) {
			checkResult= MIAO_SHA_OVER.getMessage();
			return checkResult;
		}*/
		//预减库存
		//有人说先校验库存，再扣减库存，也可以直接使用decr操作，通过返回值判断是否售罄
		Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
		if (stock < 0) {
			//localOverMap.put(goodsId, true);
			checkResult=MIAO_SHA_OVER.getMessage();
			return checkResult;
		}
		return checkResult;
	}



	public long getMiaoshaResult(Long userId, long goodsId) {
		MiaoshaOrder order = redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId,MiaoshaOrder.class) ;;
		if(order != null) {//秒杀成功
			return order.getOrderId();
		}else {
			boolean isOver = getGoodsOver(goodsId);
			if(isOver) {
				return -1;
			}else {
				return 0;
			}
		}
	}

	private void setGoodsOver(Long goodsId) {
		redisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
	}

	private boolean getGoodsOver(long goodsId) {
		return redisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);
	}

	public boolean checkPath(MiaoshaUser user, long goodsId, String path) {
		if(user == null || path == null) {
			return false;
		}
		String pathOld = redisService.get(MiaoshaKey.getMiaoshaPath, ""+user.getNickname() + "_"+ goodsId, String.class);
		return path.equals(pathOld);
	}

	public String createMiaoshaPath(MiaoshaUser user, long goodsId) {
		if(user == null || goodsId <=0) {
			return null;
		}
		String str = MD5Utils.md5(UUIDUtil.uuid()+"123456");
		redisService.set(MiaoshaKey.getMiaoshaPath, ""+user.getNickname() + "_"+ goodsId, str);
		return str;
	}

	public BufferedImage createVerifyCode(MiaoshaUser user, long goodsId) {
		if(user == null || goodsId <=0) {
			return null;
		}
		int width = 80;
		int height = 32;
		//create the image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// set the background color
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		// draw the border
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// create a random instance to generate the codes
		Random rdm = new Random();
		// make some confusion
		for (int i = 0; i < 50; i++) {
			int x = rdm.nextInt(width);
			int y = rdm.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}
		// generate a random code
		String verifyCode = generateVerifyCode(rdm);
		g.setColor(new Color(0, 100, 0));
		g.setFont(new Font("Candara", Font.BOLD, 24));
		g.drawString(verifyCode, 8, 24);
		g.dispose();
		//把验证码存到redis中
		int rnd = calc(verifyCode);
		redisService.set(MiaoshaKey.getMiaoshaVerifyCode, user.getNickname()+","+goodsId, rnd);
		//输出图片
		return image;
	}

	/**
	 * 注册时用的验证码
	 * @param verifyCode
	 * @return
	 */
	public boolean checkVerifyCodeRegister(int verifyCode) {
		Integer codeOld = redisService.get(MiaoshaKey.getMiaoshaVerifyCodeRegister,"regitser", Integer.class);
		if(codeOld == null || codeOld - verifyCode != 0 ) {
			return false;
		}
		redisService.delete(MiaoshaKey.getMiaoshaVerifyCode, "regitser");
		return true;
	}


	public BufferedImage createVerifyCodeRegister() {
		int width = 80;
		int height = 32;
		//create the image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// set the background color
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		// draw the border
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// create a random instance to generate the codes
		Random rdm = new Random();
		// make some confusion
		for (int i = 0; i < 50; i++) {
			int x = rdm.nextInt(width);
			int y = rdm.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}
		// generate a random code
		String verifyCode = generateVerifyCode(rdm);
		g.setColor(new Color(0, 100, 0));
		g.setFont(new Font("Candara", Font.BOLD, 24));
		g.drawString(verifyCode, 8, 24);
		g.dispose();
		//把验证码存到redis中
		int rnd = calc(verifyCode);
		redisService.set(MiaoshaKey.getMiaoshaVerifyCodeRegister,"regitser",rnd);
		//输出图片
		return image;
	}

	private static int calc(String exp) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			Integer catch1 = (Integer)engine.eval(exp);
			return catch1.intValue();
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode) {
		if(user == null || goodsId <=0) {
			return false;
		}
		Integer codeOld = redisService.get(MiaoshaKey.getMiaoshaVerifyCode, user.getNickname()+","+goodsId, Integer.class);
		if(codeOld == null || codeOld - verifyCode != 0 ) {
			return false;
		}
		redisService.delete(MiaoshaKey.getMiaoshaVerifyCode, user.getNickname()+","+goodsId);
		return true;
	}

	private static char[] ops = new char[] {'+', '-', '*'};
	/**
	 * + - *
	 * */
	private String generateVerifyCode(Random rdm) {
		int num1 = rdm.nextInt(10);
		int num2 = rdm.nextInt(10);
		int num3 = rdm.nextInt(10);
		char op1 = ops[rdm.nextInt(3)];
		char op2 = ops[rdm.nextInt(3)];
		String exp = ""+ num1 + op1 + num2 + op2 + num3;
		return exp;
	}





	/*
	 * 同步秒杀订单。
	 * 订单量较小时，可查询数据库。
	 * 可根据订单量设置是否读写分离
	 * */
	private OrderInfo syncMiaoSha(MiaoshaUser user, Long goodsId,int expireTime){
		OrderInfo orderInfo=new OrderInfo();
		GoodsExtVo goods = goodsComposeService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if(stock <= 0) {
			return null;
		}
		//判断是否已经秒杀到了
		MiaoshaOrder order = redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+Long.valueOf(user.getNickname())+"_"+goodsId,MiaoshaOrder.class) ;;
		if(order != null) {
			return  null;
		}
		orderInfo=this.miaoSha(user,goods,expireTime);
		return orderInfo;
	}

	/*
	 * 异步处理秒杀订单，异步处理不建议查询数据库，会是db崩溃
	 * */
	private OrderInfo asyncMiaoSha(MiaoshaUser user, Long goodsId){
		OrderInfo orderInfo=new OrderInfo();
		MiaoShaMessage mm = new MiaoShaMessage();
		mm.setGoodsId(goodsId);
		mm.setUser(user);
		String msg = StringBeanUtil.beanToString(mm);
		MQServiceFactory.create("rabbit","miaoshamessage").send(msg);
		return orderInfo;
	}

}
