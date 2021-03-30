package com.geekq.miaosha.service;



import com.geekq.miaosha.biz.entity.MiaoshaOrder;
import com.geekq.miaosha.biz.entity.MiaoshaUser;
import com.geekq.miaosha.biz.entity.OrderInfo;
import com.geekq.miaosha.rabbitmq.MQSender;
import com.geekq.miaosha.rabbitmq.MiaoshaMessage;
import com.geekq.miaosha.redis.GoodsKey;
import com.geekq.miaosha.redis.MiaoshaKey;
import com.geekq.miaosha.redis.RedisService;
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
import java.util.Random;

@Service
public class MiaoShaComposeService {
	
	@Autowired
	GoodsComposeService goodsComposeService;
	@Autowired
	OrderComposeService orderComposeService;
	@Autowired
    RedisService redisService;
	@Autowired
	MQSender mqSender;

/*真正扣减库存，并生成订单
*
* */
	public OrderInfo doMiaosha(MiaoshaUser user, GoodsExtVo goodsVo,int expireTime){
		OrderInfo orderInfo=new OrderInfo();
		boolean success=false;
		int miaoshaGoodsCount=1;
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

	    //生成秒杀订单信息
        /*如果秒杀商品库存较少时，可以直接访问数据库
        如果秒杀商品库存量大是，就需要使用消息对列队请求进行削峰
        *
        * */
	public OrderInfo miaosha(MiaoshaUser user, Long goodsId, boolean syncOrder){
		OrderInfo orderInfo=new OrderInfo();
		int expireTime=30;
		if(syncOrder){//同步订单
			orderInfo=this.syncMiaoSha(user,goodsId,expireTime);
		}else{//订单不同步,异步请求
            orderInfo=this.asyncMiaoSha(user,goodsId);
		}
		return orderInfo;
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
		MiaoshaOrder order = orderComposeService.getCachedMiaoshaOrderByUserIdGoodsId(Long.valueOf(user.getNickname()), goodsId);
		if(order != null) {
			return  null;
		}
		orderInfo=this.doMiaosha(user,goods,expireTime);
		return orderInfo;
	}

	/*
	* 异步处理秒杀订单，异步处理不建议查询数据库，会是db崩溃
	* */
	private OrderInfo asyncMiaoSha(MiaoshaUser user, Long goodsId){
		OrderInfo orderInfo=new OrderInfo();
		MiaoshaMessage mm = new MiaoshaMessage();
		mm.setGoodsId(goodsId);
		mm.setUser(user);
		mqSender.sendMiaoshaMessage(mm);
		return orderInfo;
	}



	public long getMiaoshaResult(Long userId, long goodsId) {
		MiaoshaOrder order = orderComposeService.getCachedMiaoshaOrderByUserIdGoodsId(userId, goodsId);
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

	/*取消订单时，要注意数据库和缓存的数据一致
	*
	* */
    @Transactional
	public boolean cancelOrder(OrderInfo orderInfo) {
		GoodsExtVo goods=new GoodsExtVo();
		goods.setId(orderInfo.getGoodsId());
		goods.setGoodsStock(orderInfo.getGoodsCount());
		goodsComposeService.addStock(goods);
		redisService.incr(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId());
		boolean deleteOrder= orderComposeService.deleteOrder(orderInfo.getId());
        return deleteOrder;
	}
}
