package com.jiujiu.xxgame.model;

public class User {

	private String token;

	private String name;

	private String tokenType;
	
	public User(String name, String token, String tokenType) {
		this.name = name;
		this.token = token;
		this.tokenType = tokenType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}
