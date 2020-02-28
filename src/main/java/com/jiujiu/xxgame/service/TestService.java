package com.jiujiu.xxgame.service;

import com.alibaba.fastjson.JSON;
import com.jiujiu.xxgame.redis.RedisClient;
import com.jiujiu.xxgame.redis.po.PrepaidPO;
import com.jiujiu.xxgame.redis.po.PrepaidRecord;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;

public class TestService {

    public static void main(String[] args) {
//        String type = "2030";
//        Short hexVal = Short.valueOf(type, 16);
//        System.out.println(Integer.toHexString(hexVal));
//
//        byte a = (byte)0x80;
//        byte b = (byte)0xFFFFFFFF;
//        byte c = (byte)0x0100;
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);
        Jedis jedis = RedisClient.getJedis();
        try {
            String data = jedis.hget("4d048ca9-d59a-4270-a7de-e24335cbacec", "prepaidNewTR");
            PrepaidPO po = null;
            if (!StringUtils.isEmpty(data)) {
                po = JSON.parseObject(data, PrepaidPO.class);
                System.out.println(po);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if(po.chargeRecord.size() > 1) {
                    PrepaidRecord lastRecord = po.chargeRecord.get(po.chargeRecord.size() - 1);
                }
                System.out.println(po.first_buy_record.containsKey("106"));
            }
        } catch (Exception e) {
        }

    }
}
