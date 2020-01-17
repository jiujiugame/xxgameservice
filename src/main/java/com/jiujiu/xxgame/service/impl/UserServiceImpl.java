package com.jiujiu.xxgame.service.impl;

import com.google.gson.Gson;
import com.jiujiu.xxgame.model.JiuJiuUser;
import com.jiujiu.xxgame.service.UserService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);



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

    @Override
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
