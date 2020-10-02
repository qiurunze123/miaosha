package com.geekq.miaosha.redis.redismanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

	private static JedisPool pool = null;

	private static RedisUtil ru = new RedisUtil();

	public static void main(String[] args) {
		RedisUtil redisUtil = RedisUtil.getInstance();
		redisUtil.set("test", "test");
		LOGGER.info(redisUtil.get("test"));
	}

	private RedisUtil() {
		if (pool == null) {
			String ip = "10.75.202.11";
			int port = 6379;
			JedisPoolConfig config = new JedisPoolConfig();
			// ����һ��pool�ɷ�����ٸ�jedisʵ����ͨ��pool.getResource()����ȡ��
			// �����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)��
			config.setMaxTotal(10000);
			// ����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����
			config.setMaxIdle(2000);
			// ��ʾ��borrow(����)һ��jedisʵ��ʱ�����ĵȴ�ʱ�䣬��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
			config.setMaxWaitMillis(1000 * 100);
			config.setTestOnBorrow(true);
			pool = new JedisPool(config, ip, port, 100000);
		}

	}

	public Jedis getJedis() {
		Jedis jedis = pool.getResource();
		return jedis;
	}

	public static RedisUtil getInstance() {
		return ru;
	}

	/**
	 * <p>
	 * ͨ��key��ȡ������redis�е�value
	 * </p>
	 * <p>
	 * ���ͷ�����
	 * </p>
	 * 
	 * @param key
	 * @return �ɹ�����value ʧ�ܷ���null
	 */
	public String get(String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = pool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return value;
	}

	/**
	 * <p>
	 * ��redis����key��value,���ͷ�������Դ
	 * </p>
	 * <p>
	 * ���key�Ѿ����� �򸲸�
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return �ɹ� ����OK ʧ�ܷ��� 0
	 */
	public String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.set(key, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return "0";
		} finally {
			returnResource(pool, jedis);
		}
	}

	/**
	 * <p>
	 * ɾ��ָ����key,Ҳ���Դ���һ������key������
	 * </p>
	 * 
	 * @param keys
	 *            һ��key Ҳ����ʹ string ����
	 * @return ����ɾ���ɹ��ĸ���
	 */
	public Long del(String... keys) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.del(keys);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return 0L;
		} finally {
			returnResource(pool, jedis);
		}
	}

	/**
	 * <p>
	 * ͨ��key��ָ����valueֵ׷��ֵ
	 * </p>
	 * 
	 * @param key
	 * @param str
	 * @return �ɹ����� ��Ӻ�value�ĳ��� ʧ�� ���� ��ӵ� value �ĳ��� �쳣����0L
	 */
	public Long append(String key, String str) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.append(key, str);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return 0L;
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * �ж�key�Ƿ����
	 * </p>
	 * 
	 * @param key
	 * @return true OR false
	 */
	public Boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return false;
		} finally {
			returnResource(pool, jedis);
		}
	}

	/**
	 * <p>
	 * ����key value,���key�Ѿ������򷵻�0,nx==> not exist
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return �ɹ�����1 ������� �� �����쳣 ���� 0
	 */
	public Long setnx(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setnx(key, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return 0L;
		} finally {
			returnResource(pool, jedis);
		}
	}

	/**
	 * <p>
	 * ����key value���ƶ������ֵ����Ч��
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            ��λ:��
	 * @return �ɹ�����OK ʧ�ܺ��쳣����null
	 */
	public String setex(String key, String value, int seconds) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.setex(key, seconds, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key ��offset ��ָ����λ�ÿ�ʼ��ԭ��value�滻
	 * </p>
	 * <p>
	 * �±��0��ʼ,offset��ʾ��offset�±꿪ʼ�滻
	 * </p>
	 * <p>
	 * ����滻���ַ������ȹ�С�������
	 * </p>
	 * <p>
	 * example:
	 * </p>
	 * <p>
	 * value : bigsea@zto.cn
	 * </p>
	 * <p>
	 * str : abc
	 * </p>
	 * <P>
	 * ���±�7��ʼ�滻 ����Ϊ
	 * </p>
	 * <p>
	 * RES : bigsea.abc.cn
	 * </p>
	 * 
	 * @param key
	 * @param str
	 * @param offset
	 *            �±�λ��
	 * @return �����滻�� value �ĳ���
	 */
	public Long setrange(String key, String str, int offset) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setrange(key, offset, str);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return 0L;
		} finally {
			returnResource(pool, jedis);
		}
	}

	/**
	 * <p>
	 * ͨ��������key��ȡ������value
	 * </p>
	 * 
	 * @param keys
	 *            string���� Ҳ������һ��key
	 * @return �ɹ�����value�ļ���, ʧ�ܷ���null�ļ��� ,�쳣���ؿ�
	 */
	public List<String> mget(String... keys) {
		Jedis jedis = null;
		List<String> values = null;
		try {
			jedis = pool.getResource();
			values = jedis.mget(keys);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return values;
	}

	/**
	 * <p>
	 * ����������key:value,����һ��
	 * </p>
	 * <p>
	 * example:
	 * </p>
	 * <p>
	 * obj.mset(new String[]{"key2","value1","key2","value2"})
	 * </p>
	 * 
	 * @param keysvalues
	 * @return �ɹ�����OK ʧ�� �쳣 ���� null
	 *
	 */
	public String mset(String... keysvalues) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.mset(keysvalues);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ����������key:value,����һ��,���key�Ѿ��������ʧ��,������ع�
	 * </p>
	 * <p>
	 * example:
	 * </p>
	 * <p>
	 * obj.msetnx(new String[]{"key2","value1","key2","value2"})
	 * </p>
	 * 
	 * @param keysvalues
	 * @return �ɹ�����1 ʧ�ܷ���0
	 */
	public Long msetnx(String... keysvalues) {
		Jedis jedis = null;
		Long res = 0L;
		try {
			jedis = pool.getResource();
			res = jedis.msetnx(keysvalues);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ����key��ֵ,������һ����ֵ
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return ��ֵ ���key������ �򷵻�null
	 */
	public String getset(String key, String value) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.getSet(key, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ���±� ��key ��ȡָ���±�λ�õ� value
	 * </p>
	 * 
	 * @param key
	 * @param startOffset
	 *            ��ʼλ�� ��0 ��ʼ ������ʾ���ұ߿�ʼ��ȡ
	 * @param endOffset
	 * @return ���û�з���null
	 */
	public String getrange(String key, int startOffset, int endOffset) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.getrange(key, startOffset, endOffset);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key ��value���м�ֵ+1����,��value����int����ʱ�᷵�ش���,��key����������valueΪ1
	 * </p>
	 * 
	 * @param key
	 * @return ��ֵ��Ľ��
	 */
	public Long incr(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.incr(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ָ����value��ֵ,���key������,������valueΪ��ֵ
	 * </p>
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long incrBy(String key, Long integer) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.incrBy(key, integer);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ��key��ֵ����������,���key������,������keyΪ-1
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.decr(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ��ȥָ����ֵ
	 * </p>
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public Long decrBy(String key, Long integer) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.decrBy(key, integer);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡvalueֵ�ĳ���
	 * </p>
	 * 
	 * @param key
	 * @return ʧ�ܷ���null
	 */
	public Long serlen(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.strlen(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��field����ָ����ֵ,���key������,���ȴ���
	 * </p>
	 * 
	 * @param key
	 * @param field
	 *            �ֶ�
	 * @param value
	 * @return ������ڷ���0 �쳣����null
	 */
	public Long hset(String key, String field, String value) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hset(key, field, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��field����ָ����ֵ,���key���������ȴ���,���field�Ѿ�����,����0
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hsetnx(String key, String field, String value) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hsetnx(key, field, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��keyͬʱ���� hash�Ķ��field
	 * </p>
	 * 
	 * @param key
	 * @param hash
	 * @return ����OK �쳣����null
	 */
	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hmset(key, hash);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key �� field ��ȡָ���� value
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @return û�з���null
	 */
	public String hget(String key, String field) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hget(key, field);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key �� fields ��ȡָ����value ���û�ж�Ӧ��value�򷵻�null
	 * </p>
	 * 
	 * @param key
	 * @param fields
	 *            ����ʹ һ��String Ҳ������ String����
	 * @return
	 */
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hmget(key, fields);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ָ����field��value���ϸ�����ֵ
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hincrby(String key, String field, Long value) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hincrBy(key, field, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��field�ж��Ƿ���ָ����value����
	 * </p>
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean hexists(String key, String field) {
		Jedis jedis = null;
		Boolean res = false;
		try {
			jedis = pool.getResource();
			res = jedis.hexists(key, field);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����field������
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long hlen(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hlen(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;

	}

	/**
	 * <p>
	 * ͨ��key ɾ��ָ���� field
	 * </p>
	 * 
	 * @param key
	 * @param fields
	 *            ������ һ�� field Ҳ������ һ������
	 * @return
	 */
	public Long hdel(String key, String... fields) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hdel(key, fields);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key�������е�field
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> hkeys(String key) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hkeys(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key�������к�key�йص�value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public List<String> hvals(String key) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hvals(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡ���е�field��value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetall(String key) {
		Jedis jedis = null;
		Map<String, String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.hgetAll(key);
		} catch (Exception e) {
			// TODO
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��listͷ������ַ���
	 * </p>
	 * 
	 * @param key
	 * @param strs
	 *            ����ʹһ��string Ҳ����ʹstring����
	 * @return ����list��value����
	 */
	public Long lpush(String key, String... strs) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.lpush(key, strs);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��listβ������ַ���
	 * </p>
	 * 
	 * @param key
	 * @param strs
	 *            ����ʹһ��string Ҳ����ʹstring����
	 * @return ����list��value����
	 */
	public Long rpush(String key, String... strs) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.rpush(key, strs);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��listָ����λ��֮ǰ����֮�� ����ַ���Ԫ��
	 * </p>
	 * 
	 * @param key
	 * @param where
	 *            LIST_POSITIONö������
	 * @param pivot
	 *            list�����value
	 * @param value
	 *            ��ӵ�value
	 * @return
	 */
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.linsert(key, where, pivot, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����listָ���±�λ�õ�value
	 * </p>
	 * <p>
	 * ����±곬��list����value�ĸ����򱨴�
	 * </p>
	 * 
	 * @param key
	 * @param index
	 *            ��0��ʼ
	 * @param value
	 * @return �ɹ�����OK
	 */
	public String lset(String key, Long index, String value) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.lset(key, index, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key�Ӷ�Ӧ��list��ɾ��ָ����count�� �� value��ͬ��Ԫ��
	 * </p>
	 * 
	 * @param key
	 * @param count
	 *            ��countΪ0ʱɾ��ȫ��
	 * @param value
	 * @return ���ر�ɾ���ĸ���
	 */
	public Long lrem(String key, long count, String value) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.lrem(key, count, value);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����list�д�strat�±꿪ʼ��end�±������valueֵ
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return �ɹ�����OK
	 */
	public String ltrim(String key, long start, long end) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.ltrim(key, start, end);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��list��ͷ��ɾ��һ��value,�����ظ�value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	synchronized public String lpop(String key) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.lpop(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��listβ��ɾ��һ��value,�����ظ�Ԫ��
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	synchronized public String rpop(String key) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.rpop(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��һ��list��β��ɾ��һ��value����ӵ���һ��list��ͷ��,�����ظ�value
	 * </p>
	 * <p>
	 * �����һ��listΪ�ջ��߲������򷵻�null
	 * </p>
	 * 
	 * @param srckey
	 * @param dstkey
	 * @return
	 */
	public String rpoplpush(String srckey, String dstkey) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.rpoplpush(srckey, dstkey);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡlist��ָ���±�λ�õ�value
	 * </p>
	 * 
	 * @param key
	 * @param index
	 * @return ���û�з���null
	 */
	public String lindex(String key, long index) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.lindex(key, index);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����list�ĳ���
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long llen(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.llen(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡlistָ���±�λ�õ�value
	 * </p>
	 * <p>
	 * ���start Ϊ 0 end Ϊ -1 �򷵻�ȫ����list�е�value
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.lrange(key, start, end);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ָ����set�����value
	 * </p>
	 * 
	 * @param key
	 * @param members
	 *            ������һ��String Ҳ������һ��String����
	 * @return ��ӳɹ��ĸ���
	 */
	public Long sadd(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.sadd(key, members);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��keyɾ��set�ж�Ӧ��valueֵ
	 * </p>
	 * 
	 * @param key
	 * @param members
	 *            ������һ��String Ҳ������һ��String����
	 * @return ɾ���ĸ���
	 */
	public Long srem(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.srem(key, members);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key���ɾ��һ��set�е�value�����ظ�ֵ
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String spop(String key) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.spop(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡset�еĲ
	 * </p>
	 * <p>
	 * �Ե�һ��setΪ��׼
	 * </p>
	 * 
	 * @param keys
	 *            ����ʹһ��string �򷵻�set�����е�value Ҳ������string����
	 * @return
	 */
	public Set<String> sdiff(String... keys) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.sdiff(keys);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡset�еĲ�����뵽��һ��key��
	 * </p>
	 * <p>
	 * �Ե�һ��setΪ��׼
	 * </p>
	 * 
	 * @param dstkey
	 *            ������key
	 * @param keys
	 *            ����ʹһ��string �򷵻�set�����е�value Ҳ������string����
	 * @return
	 */
	public Long sdiffstore(String dstkey, String... keys) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.sdiffstore(dstkey, keys);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡָ��set�еĽ���
	 * </p>
	 * 
	 * @param keys
	 *            ����ʹһ��string Ҳ������һ��string����
	 * @return
	 */
	public Set<String> sinter(String... keys) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.sinter(keys);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡָ��set�еĽ��� ������������µ�set��
	 * </p>
	 * 
	 * @param dstkey
	 * @param keys
	 *            ����ʹһ��string Ҳ������һ��string����
	 * @return
	 */
	public Long sinterstore(String dstkey, String... keys) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.sinterstore(dstkey, keys);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��������set�Ĳ���
	 * </p>
	 * 
	 * @param keys
	 *            ����ʹһ��string Ҳ������һ��string����
	 * @return
	 */
	public Set<String> sunion(String... keys) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.sunion(keys);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��������set�Ĳ���,�����뵽�µ�set��
	 * </p>
	 * 
	 * @param dstkey
	 * @param keys
	 *            ����ʹһ��string Ҳ������һ��string����
	 * @return
	 */
	public Long sunionstore(String dstkey, String... keys) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.sunionstore(dstkey, keys);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��set�е�value�Ƴ�����ӵ��ڶ���set��
	 * </p>
	 * 
	 * @param srckey
	 *            ��Ҫ�Ƴ���
	 * @param dstkey
	 *            ��ӵ�
	 * @param member
	 *            set�е�value
	 * @return
	 */
	public Long smove(String srckey, String dstkey, String member) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.smove(srckey, dstkey, member);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡset��value�ĸ���
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long scard(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.scard(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key�ж�value�Ƿ���set�е�Ԫ��
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Boolean sismember(String key, String member) {
		Jedis jedis = null;
		Boolean res = null;
		try {
			jedis = pool.getResource();
			res = jedis.sismember(key, member);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡset�������value,��ɾ��Ԫ��
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String srandmember(String key) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.srandmember(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡset�����е�value
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.smembers(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��zset�����value,score,����score�������������
	 * </p>
	 * <p>
	 * �����value�Ѿ����������score����Ԫ��
	 * </p>
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(String key, double score, String member) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zadd(key, score, member);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��keyɾ����zset��ָ����value
	 * </p>
	 * 
	 * @param key
	 * @param members
	 *            ����ʹһ��string Ҳ������һ��string����
	 * @return
	 */
	public Long zrem(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zrem(key, members);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key���Ӹ�zset��value��score��ֵ
	 * </p>
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Double zincrby(String key, double score, String member) {
		Jedis jedis = null;
		Double res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zincrby(key, score, member);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����zset��value������
	 * </p>
	 * <p>
	 * �±��С��������
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrank(String key, String member) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zrank(key, member);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����zset��value������
	 * </p>
	 * <p>
	 * �±�Ӵ�С����
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(String key, String member) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zrevrank(key, member);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����ȡscore��start��end��zset��value
	 * </p>
	 * <p>
	 * socre�Ӵ�С����
	 * </p>
	 * <p>
	 * ��startΪ0 endΪ-1ʱ����ȫ��
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long end) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zrevrange(key, start, end);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����ָ��score��zset�е�value
	 * </p>
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public Set<String> zrangebyscore(String key, String max, String min) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����ָ��score��zset�е�value
	 * </p>
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public Set<String> zrangeByScore(String key, double max, double min) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ����ָ��������zset��value������
	 * </p>
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long zcount(String key, String min, String max) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zcount(key, min, max);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key����zset�е�value����
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Long zcard(String key) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zcard(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key��ȡzset��value��scoreֵ
	 * </p>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key, String member) {
		Jedis jedis = null;
		Double res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zscore(key, member);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��keyɾ�����������ڵ�Ԫ��
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByRank(String key, long start, long end) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zremrangeByRank(key, start, end);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��keyɾ��ָ��score�ڵ�Ԫ��
	 * </p>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByScore(String key, double start, double end) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = pool.getResource();
			res = jedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ��������pattern���ʽ������key
	 * </p>
	 * <p>
	 * keys(*)
	 * </p>
	 * <p>
	 * �������е�key
	 * </p>
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = pool.getResource();
			res = jedis.keys(pattern);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * <p>
	 * ͨ��key�ж�ֵ������
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public String type(String key) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = pool.getResource();
			res = jedis.type(key);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
		} finally {
			returnResource(pool, jedis);
		}
		return res;
	}

	/**
	 * ���������ӳ�
	 *
	 * @param pool
	 * @param jedis
	 */
	public static void returnResource(JedisPool pool, Jedis jedis) {
		if (jedis != null) {
			pool.returnResourceObject(jedis);
		}
	}

	/**
	 * ���������ӳ�
	 *
	 * @param pool
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		if (jedis != null) {
			pool.returnResourceObject(jedis);
		}
	}
}
