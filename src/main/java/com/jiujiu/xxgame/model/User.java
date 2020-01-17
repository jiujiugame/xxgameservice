package com.jiujiu.xxgame.model;

public class User {

	private String token;
	
	private String tokenType;
	
	public User(String token, String tokenType) {
		super();
		this.token = token;
		this.tokenType = tokenType;
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
