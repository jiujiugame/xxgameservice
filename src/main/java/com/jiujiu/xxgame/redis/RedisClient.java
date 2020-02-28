package com.jiujiu.xxgame.redis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class RedisClient {

    private static String IP = "xx1.jiujiuapp.cn";
//    private static String IP = "127.0.0.1";

    private static int PORT = 6379;

    private static int MAX_ACTIVE = 100;
    private static int MAX_IDLE = 20;
    private static int MAX_WAIT = 3000;
    private static int TIMEOUT = 3000;
    private static boolean TEST_ON_BORROW = true;
    private static boolean TEST_ON_RETURN = true;

    private static JedisPool jedisPool = null;

    public final static int EXRP_HOUR = 60*60;
    public final static int EXRP_DAY = 60*60*24;
    public final static int EXRP_MONTH = 60*60*24*30;

    private static void initialPool(){
        try{
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config,IP, PORT, TIMEOUT);
        }catch(Exception e) {
            //logger.error("First create JedisPool error : "+e);
            e.getMessage();
        }
    }

    private static synchronized void poolInit() {
        if(jedisPool == null) {
            initialPool();
        }
    }

    public synchronized static Jedis getJedis() {
        if(jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try{
            if(jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        }catch(Exception e) {
            e.printStackTrace();
            // logger.error("Get jedis error : "+e);
        }finally{
            jedis.close();
        }
        return jedis;
    }

    public static Set<String> keys(final String pattern) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String select(final int index) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.select(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

//    private static Jedis jedis;
//    static {
//        jedis = new Jedis("127.0.0.1", 6379);
////        jedis.auth("123456");
//    }

    public static void main(String[] args) {
        Jedis jedis = RedisClient.getJedis();
        try {
            jedis.select(1);
            System.out.println(jedis.keys("*"));

            Set<String> keys = jedis.hkeys("NAME_MODULE");
            for (String key : keys) {
                System.out.println("key:" + key);
                System.out.println(jedis.hget("NAME_MODULE", key));
                System.out.println(jedis.hget("NAME_MODULE", "敬凌宇"));
            }

            System.out.println();
        } finally {
            jedis.close();
        }
    }
}
