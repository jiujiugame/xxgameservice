package com.jiujiu.xxgame.service;

import com.google.gson.Gson;
import com.jiujiu.xxgame.model.JiuJiuUser;
import com.jiujiu.xxgame.model.Load;
import com.jiujiu.xxgame.model.LoginResult;
import com.jiujiu.xxgame.model.OnlineStats;
import com.jiujiu.xxgame.model.User;

import com.jiujiu.xxgame.redis.RedisClient;
import com.jiujiu.xxgame.redis.RedisKeys;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.InputStream;

@Component
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

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



    private static class JiuJiuResp {
        private int status;

        private String message;

        private Account account;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        static class Account {
            private JiuJiuUser account;

            public JiuJiuUser getAccount() {
                return account;
            }

            public void setAccount(JiuJiuUser account) {
                this.account = account;
            }
        }
    }

    public JiuJiuUser getJiuJiuUser(String token) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://www.jiujiuapp.cn/app/api/userinfo");
        post.setHeader("Authorization", "Bearer " + token);

        try(CloseableHttpResponse response = client.execute(post)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                try (InputStream inputStream = httpEntity.getContent()) {
                    String resp = IOUtils.toString(inputStream, "UTF-8");
                    logger.info("userinfo {}", resp);
                    Gson gson = new Gson();
                    JiuJiuResp jjresp = gson.fromJson(resp, JiuJiuResp.class);
                    if(jjresp.status == 1) {
                        return jjresp.getAccount().getAccount();
                    } else
                        return null;
                }
            }
        } catch (Exception e) {
            logger.error("http requet error", e);
        }
        return null;
    }


}
