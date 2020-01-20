package com.jiujiu.xxgame.service;

import com.google.gson.Gson;
import com.jiujiu.xxgame.model.*;

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
import java.util.*;

@Component
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public LoginResult checkLogin(String tokenHeader) {
        LoginResult result = new LoginResult();
        System.out.println("tokenHeader:"+tokenHeader);
        JiuJiuUser juser = this.getJiuJiuUser(tokenHeader);
        if (juser == null || juser.getUid() == null) {
            result.setNewUid("");
            result.setS2c_code(-1);
            result.setS2c_msg("啾啾登录失效，请重新登录。");
            return result;
        }

        result.setToken(RedisKeys.newToken());
        result.setNewUid(juser.getUid());
//        result.setNewBid(RedisKeys.newToken());
        User user = new User(juser.getUid());
//        user.setMac(mac);

        Jedis jedis = RedisClient.getJedis();
        try {
            String accountKey = RedisKeys.getAccountKey(juser.getUid());
//            String tokenKey = RedisKeys.getTokenKey(result.getToken());
            jedis.select(2);
            if (jedis.exists(accountKey)) {
                //LOGIN:
                String bid = jedis.get(accountKey);
                result.setToken(bid);
            } else {
                //REG:
                jedis.set(accountKey, result.getToken());
            }
//            jedis.setex(tokenKey, 600, user.toString());
//            jedis.select(3);
//            jedis.publish(RedisKeys.KEY_SERVER_LOGIN, new Load().toString());
//            jedis.publish(RedisKeys.KEY_DATA_ONLINECOUNT, new OnlineStats().toString());
        } finally {
            jedis.close();
        }

        result.setBoard(this.readServerList());
//        System.out.println("checkLogin failed:" + tokenHeader);
        return result;
    }


    public String readServerList() {
        Jedis jedis = RedisClient.getJedis();
        try {
            jedis.select(2);
            Map<String, String> areas = jedis.hgetAll(RedisKeys.KEY_AREA_LIST);
            Map<String, List<Server>> vals = new HashMap<>();

            Map<String, String> servers = jedis.hgetAll(RedisKeys.KEY_SERVER_LIST);

            for(String key : areas.keySet()){
                vals.put(key, new ArrayList<Server>());
            }

            for(String key : servers.keySet()){
                String value = servers.get(key);
                Gson gson = new Gson();
                Server server = gson.fromJson(value, Server.class);
                System.out.println(server.getServerString());
                List serverList = vals.get(server.getAreaId().toString());
                serverList.add(server);
                vals.put(server.getAreaId().toString(), serverList);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for(String key : vals.keySet()){
                sb.append("{\"服务器\":[");
                List<Server> servList = vals.get(key);
                for(Server s : servList) {
                    sb.append(s.getServerString());
                }
                sb.append("]},");
            }
            sb.append("]");
            return sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            jedis.close();
        }
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

    public JiuJiuUser getJiuJiuUser(String tokenHeader) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://www.jiujiuapp.cn/app/api/userinfo");
        post.setHeader("Authorization", tokenHeader);
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
//    	System.out.println(service.getJiuJiuUser("bearer edc6be36-76a3-4a77-a087-42e9f840bd5b"));
        System.out.println(service.readServerList());
    }

}
