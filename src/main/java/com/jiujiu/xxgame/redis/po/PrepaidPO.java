package com.jiujiu.xxgame.redis.po;

import java.util.*;

public class PrepaidPO  {
	public String playerId;
	/**
	 * 首次购买的记录,用于判断首次购买的额外奖励
	 */
	public Map<Integer, Integer> first_buy_record = new HashMap<>();

	/**
	 * 首冲多少钱
	 */
	public int firstCharge;

	/**
	 * 累计充值
	 */
	public int total_charge;

	/**
	 * 累计消费
	 */
	public int total_consume;

	/**
	 * 当日充值所获得的元宝
	 */
	public int dailyChargeDiamond;
	
	/**
	 * 今天充值多少人民币
	 */
	public int dailyPayRmb;

	public List<PrepaidRecord> chargeRecord = new ArrayList<>();
	public Date dailyDate;

	// public Map<Integer, Boolean> superPackageTodayMap = new HashMap<>();

	@Override
	public String toString() {
		return "PrepaidPO{" +
				"playerId='" + playerId + '\'' +
				", first_buy_record=" + first_buy_record +
				", firstCharge=" + firstCharge +
				", total_charge=" + total_charge +
				", total_consume=" + total_consume +
				", dailyChargeDiamond=" + dailyChargeDiamond +
				", dailyPayRmb=" + dailyPayRmb +
				", chargeRecord=" + chargeRecord +
				", dailyDate=" + dailyDate +
				'}';
	}
}
