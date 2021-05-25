package com.geekq.miaosha.redis;


import com.geekq.miaosha.utils.StringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;


	/**
	 * 获取当个对象
	 * */
	public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
		      T t=null;
			 //生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 Object tempStr=redisTemplate.opsForValue().get(realKey);
			 if(null!=tempStr){
				 String  str = tempStr.toString();
			     t = StringBeanUtil.stringToBean(str, clazz);
			 }

			 return t;

	}

    public  String get(String key){

        String result = null;
        try {

            result = redisTemplate.opsForValue().get(key).toString();
        } catch (Exception e) {
            log.error("expire key:{} error",key,e);

            return result;
        }
        ;
        return result;
    }



	/**
	 * 设置对象
	 * */
	public <T> boolean set(KeyPrefix prefix, String key, T value) {
			 String str = StringBeanUtil.beanToString(value);
			 if(str == null || str.length() <= 0) {
				 return false;
			 }
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 int seconds =  prefix.expireSeconds();

			 if(seconds <= 0) {

				 redisTemplate.opsForValue().set(realKey, str);
			 }else {
				 redisTemplate.opsForValue().set(realKey, str );
			 }

			 return true;

	}
	
	/**
	 * 判断key是否存在
	 * */
	public <T> boolean exists(KeyPrefix prefix, String key) {
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  redisTemplate.hasKey(realKey);

	}
	
	/**
	 * 删除
	 * */
	public boolean delete(KeyPrefix prefix, String key) {



			//生成真正的key
			String realKey  = prefix.getPrefix() + key;
			boolean ret =  redisTemplate.delete(realKey);
			return ret ;

	}
	
	/**
	 * 增加值
	 * */
	public <T> Long incr(KeyPrefix prefix, String key) {
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  redisTemplate.opsForValue().increment(realKey);

	}
	
	/**
	 * 减少值
	 * */
	public <T> Long decr(KeyPrefix prefix, String key) {
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  redisTemplate.opsForValue().decrement(realKey);

	}




	public boolean delete(KeyPrefix prefix) {
		if(prefix == null) {
			return false;
		}
		List<String> keys = scanKeys(prefix.getPrefix());
		if(keys==null || keys.size() <= 0) {
			return true;
		}

		try {

			//redisTemplate.delete(keys.toArray(new String[0]));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<String> scanKeys(String key) {

			List<String> keys = new ArrayList<String>();
		/*	String cursor = "0";
			ScanParams sp = new ScanParams();
			sp.match("*"+key+"*");
			sp.count(100);
			do{
				ScanResult<String> ret = redisTemplate.opsForList().(cursor, sp);
				List<String> result = ret.getResult();
				if(result!=null && result.size() > 0){
					keys.addAll(result);
				}
				//再处理cursor

			}while(!cursor.equals("0"));
			*/
			return keys;

	}
	




}
