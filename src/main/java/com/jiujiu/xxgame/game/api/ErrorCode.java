package com.jiujiu.xxgame.game.api;

/**
 * 错误编码.
 *
 * @author 小流氓(mingkai.zhou@qeng.net)
 */
public class ErrorCode {
	/**
	 * 操作成功
	 */
	public static final int OK = 0;
	/**
	 * 非法操作
	 */
	public static final int ERROR = 1;

	/**
	 * 服务器未找到，用来表示正在维护.
	 */
	public static final String SERVER_NOT_FOUND = "SERVER_NOT_FOUND";
	/**
	 * 指定目标未找到，用来表示查询无果.
	 */
	public static final String TARGET_NOT_FOUND = "TARGET_NOT_FOUND";
	/**
	 * 玩家未找到，请确认输出的玩家名称.
	 */
	public static final String PLAYER_NOT_FOUND = "PLAYER_NOT_FOUND";

	/**
	 * 登录操作
	 */
	// 没有这个用户名称
	public static final int ACCOUNT_DOES_NOT_EXIST = 1001;
	// 密码错误
	public static final int PASSWORD_ERROR = 1002;
	/** 账号已锁定 */
	public static final int ACCOUNT_STATUS_LOCK = 1003;
	/** 连错10次，那10分钟后再尝试... */
	public static final int ACCOUNT_STATUS_EXCEPTION = 1004;
	/** IP访问限制 */
	public static final int IP_ACCESS_RESTRICTION = 1005;

	/** 没有找到该玩家 */
	public static final int THE_PLAYER_NOT_FOUND = 3;
}