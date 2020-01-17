package com.jiujiu.xxgame.service;

import com.jiujiu.xxgame.model.Load;
import com.jiujiu.xxgame.model.LoginResult;
import com.jiujiu.xxgame.model.OnlineStats;
import com.jiujiu.xxgame.model.User;

import com.jiujiu.xxgame.redis.RedisClient;
import com.jiujiu.xxgame.redis.RedisKeys;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class UserService {

    public LoginResult checkLogin(String token, String uid) {
        LoginResult result = new LoginResult();
        result.setToken(RedisKeys.newToken());

        User user = new User(uid);

        Jedis jedis = RedisClient.getJedis();
        try {
            String accountKey = RedisKeys.getAccountKey(uid);
            String tokenKey = RedisKeys.getTokenKey(result.getToken());
            if(jedis.exists(accountKey)) {
                //LOGIN:
            } else {
                //REG:
                jedis.set(accountKey, "$%^#$%#asGS$%!@134234");
            }
            jedis.setex(tokenKey, 600, user.toString());
            jedis.publish(RedisKeys.KEY_SERVER_LOGIN, new Load().toString());
            jedis.publish(RedisKeys.KEY_DATA_ONLINECOUNT, new OnlineStats().toString());
        } finally {
            jedis.close();
        }

        System.out.println("checkLogin failed:" + token);
        return result;
    }

}
