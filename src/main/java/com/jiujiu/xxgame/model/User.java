package com.jiujiu.xxgame.model;

public class User {

	private String uid;

	private String os = "0";
	
	private String channel = "";

	private String mac = "";
	
	public User(String uid) {
		super();
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public String toString() {
		return "{" +
				"\"uid\" : \"" + uid + "\"" +
				",\"os\" : \"" + os + "\"" +
				",\"channel\" : \"" + channel + "\"" +
				",\"mac\" : \"" + mac + "\"" +
				'}';
	}
}
