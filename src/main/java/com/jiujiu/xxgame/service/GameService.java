package com.jiujiu.xxgame.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jiujiu.xxgame.game.GGlobal;
import com.jiujiu.xxgame.game.GServer;
import com.jiujiu.xxgame.game.api.PayAPI;
import com.jiujiu.xxgame.game.api.rpc.RpcResponse;
import com.jiujiu.xxgame.model.ChargeResult;
import com.jiujiu.xxgame.redis.RedisClient;
import com.jiujiu.xxgame.redis.RedisKeys;
import com.jiujiu.xxgame.redis.po.PrepaidPO;
import com.jiujiu.xxgame.redis.po.PrepaidRecord;
import io.netty.util.internal.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class GameService {

    private boolean isLoggedIn = false;

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CloseableHttpClient client = HttpClientBuilder.create().build();

    public static List<NameValuePair> transformMap(Map<String, String> params) {
        if (params == null || params.size() < 0) {// 如果参数为空则返回null;
            return new ArrayList<NameValuePair>();
        }
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> map : params.entrySet()) {
            paramList.add(new BasicNameValuePair(map.getKey(), map.getValue()));
        }
        return paramList;
    }

    public String getLastOrderId(String gameRoleId) {
        Jedis jedis = RedisClient.getJedis();
        try {
            String data = jedis.hget(gameRoleId, "prepaidNewTR");
            if (!StringUtils.isEmpty(data)) {
                PrepaidPO po = JSON.parseObject(data, PrepaidPO.class);

                if(po.chargeRecord.size() > 1) {
                    PrepaidRecord lastRecord = po.chargeRecord.get(po.chargeRecord.size() - 1);
                    return sdf.format(lastRecord.date);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    public ChargeResult charge(String gameZoneId, String gameRoleId, String productId, String orderId) {
        if(!this.isLoggedIn) {
            boolean loggedIn = this.login();
            if(!loggedIn) {
                return new ChargeResult(0, "充值失败, GM发货登录不上。", orderId);
            }
        }

        HttpPost post = new HttpPost("http://xx1.jiujiuapp.cn:8099/xmds-gm/action/execute");
        post.setHeader("Accept-Charset", "UTF-8");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> params = new HashMap<String, String>();
        params.put("type","2030");
        params.put("value","[\"id\",\""+gameRoleId+"\","+productId+"]");
        params.put("servers",gameZoneId);
        List<NameValuePair> paramList = transformMap(params);
        UrlEncodedFormEntity formEntity = null;
        try {
            formEntity = new UrlEncodedFormEntity(paramList, "utf8");
            post.setEntity(formEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ChargeResult(0, e.getMessage(), orderId);
        }

        try(CloseableHttpResponse response = client.execute(post)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                try (InputStream inputStream = httpEntity.getContent()) {
                    String resp = IOUtils.toString(inputStream, "UTF-8");
                    Gson gson = new Gson();
                    System.out.println(">>>>>>>>CHARGE-RETURN:"+resp);

                    JsonObject obj = gson.fromJson(resp, JsonObject.class);
                    if(obj.get(gameZoneId) != null) {
                        int retVal = obj.get(gameZoneId).getAsInt();
                        if(retVal == 1) {
                            return new ChargeResult(1, "充值成功", orderId);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    this.isLoggedIn = false;
                    this.login();
                    return new ChargeResult(0, e.getMessage(), orderId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ChargeResult(0, e.getMessage(), orderId);
        }
        return new ChargeResult(0, "充值失败", orderId);
    }

    public boolean login() {
        HttpPost post = new HttpPost("http://xx1.jiujiuapp.cn:8099/xmds-gm/action/login");
//        post.setHeader("Authorization", tokenHeader);
        post.setHeader("Accept-Charset", "UTF-8");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> params = new HashMap<String, String>();
        params.put("uname","gm");
        params.put("pwd","123456");
        List<NameValuePair> paramList = transformMap(params);
        UrlEncodedFormEntity formEntity = null;
        try {
            formEntity = new UrlEncodedFormEntity(paramList, "utf8");
            post.setEntity(formEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.isLoggedIn = false;
        }

        try(CloseableHttpResponse response = client.execute(post)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                try (InputStream inputStream = httpEntity.getContent()) {
                    String resp = IOUtils.toString(inputStream, "UTF-8");
                    Gson gson = new Gson();
                    System.out.println(resp);
                    this.isLoggedIn = true;
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    this.isLoggedIn = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.isLoggedIn = false;
        }
        return false;
    }

    public static void servers(CloseableHttpClient client) throws UnsupportedEncodingException {
        HttpGet get = new HttpGet("http://xx1.jiujiuapp.cn:8099/xmds-gm/action/servers");

        try(CloseableHttpResponse response = client.execute(get)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                try (InputStream inputStream = httpEntity.getContent()) {
                    String resp = IOUtils.toString(inputStream, "UTF-8");
                    Gson gson = new Gson();
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>servers：");
                    System.out.println(resp);
//                    JiuJiuResp jjresp = gson.fromJson(resp, JiuJiuResp.class);
//                    if(jjresp.status == 1) {
//                        return jjresp.getData().getAccount();
//                    } else
//                        return null;
                }
            } else {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>ERROR：");
                System.out.println(response.getStatusLine().getStatusCode());

            }
        } catch (Exception e) {
            e.printStackTrace();
//            logger.error("http requet error", e);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        ChargeResult re = new GameService().charge("123", "4d048ca9-d59a-4270-a7de-e24335cbacec", "106", "1212");
        System.out.println(re);
    }

}
