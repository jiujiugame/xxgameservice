package com.jiujiu.xxgame.redis;

import java.util.UUID;

public class RedisKeys {
    public static final String KEY_ACCOUNT_PREFIX = "/account/";
    public static final String KEY_SERVER_BLACK_IP = "/server/blacklist/ip";
    public static final String KEY_SERVER_BLACK_UID = "/server/blacklist/uid";
    public static final String KEY_TOEKN_PREFIX = "/token/";
    public static final String KEY_SERVER_LOGIN = "/server/login";
    public static final String KEY_DATA_ONLINECOUNT = "data.OnlineCount";
    public static final String KEY_PLAYER_SERVERS_PREFIX = "/player/servers/";
    public static final String KEY_SERVER_LIST = "/serverlist/80";
    public static final String KEY_AREA_LIST = "/arealist/80";


    public static String getTokenKey(final String token) {
        return KEY_TOEKN_PREFIX + token;
    }

    public static String getAccountKey(final String uid) {
        return KEY_ACCOUNT_PREFIX + uid;
    }

    public static String getPlayerServerKey(final String uid) {
        return KEY_PLAYER_SERVERS_PREFIX + uid;
    }

    public static String newToken() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.print(UUID.randomUUID().toString());
    }

}