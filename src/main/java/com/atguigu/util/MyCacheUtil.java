package com.atguigu.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;

import redis.clients.jedis.Jedis;

public class MyCacheUtil {

	public static boolean if_key(String key) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {

		}
		Boolean exists = jedis.exists(key);
		return exists;
	}

	public static int inter_redis_key(String dis_key, List<T_MALL_SKU_ATTR_VALUE> list_attr_value) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			return 1;
		}

		String[] keys = new String[list_attr_value.size()];// attr_14_37
		for (int i = 0; i < list_attr_value.size(); i++) {
			keys[i] = "attr_" + list_attr_value.get(i).getShxm_id() + "_" + list_attr_value.get(i).getShxzh_id();
		}
		jedis.zinterstore(dis_key, keys);

		return 0;
	}

	public static <T> int refresh_redis_key(String key, List<T> list) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			return 1;
		}

		jedis.del(key);

		for (int i = 0; i < list.size(); i++) {
			String object_to_json = MyJsonUtil.object_to_json(list.get(i));
			jedis.zadd(key, i, object_to_json);
		}

		return 0;

	}

	public static <T> List<T> get_redis_key(String key, Class<T> t) {

		List<T> list = new ArrayList<T>();

		Jedis jedis = null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			return null;
		}

		Set<String> zrange = jedis.zrange(key, 0, -1);

		Iterator<String> iterator = zrange.iterator();

		while (iterator.hasNext()) {
			String next = iterator.next();

			T json_to_object = MyJsonUtil.json_to_object(next, t);

			list.add(json_to_object);
		}

		return list;
	}

}
