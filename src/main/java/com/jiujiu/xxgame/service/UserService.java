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

        JiuJiuUser juser = this.getJiuJiuUser(token);
        if(juser == null || juser.getUid() == null) {
        	result.setNewUid("");
        	result.setS2c_code(-1);
        	result.setS2c_msg("啾啾登录失效，请重新登录。");
        	return result;
        }
        
        result.setToken(RedisKeys.newToken());
        result.setNewUid(juser.getUid());
        User user = new User(juser.getUid());

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

        private Account data;

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

        public Account getData() {
            return data;
        }

        public void setData(Account data) {
            this.data = data;
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
        post.setHeader("Authorization", token);
        post.setHeader("Accept-Charset", "UTF-8");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        try(CloseableHttpResponse response = client.execute(post)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                try (InputStream inputStream = httpEntity.getContent()) {
                    String resp = IOUtils.toString(inputStream, "UTF-8");
                    logger.info("userinfo {}", resp);
                    Gson gson = new Gson();
                    JiuJiuResp jjresp = gson.fromJson(resp, JiuJiuResp.class);
                    if(jjresp.status == 1) {
                        return jjresp.getData().getAccount();
                    } else
                        return null;
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("http requet error", e);
        }
        return null;
    }
    
    public static void main(String[] args) {
//    	String url = "https://jiujiuapp.cn/app/oauth/authorize?client_id=mmm&redirect_uri=https://www.baidu.com&response_type=token";
    	UserService service = new UserService();  
    	System.out.println(service.getJiuJiuUser("bearer edc6be36-76a3-4a77-a087-42e9f840bd5b"));
    }


}
