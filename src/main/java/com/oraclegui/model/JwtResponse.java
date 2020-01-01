package com.oraclegui.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String jwtToken;
	
	public JwtResponse(String jwttToken) {
		this.jwtToken = jwttToken;
	}
	
	public String getToken() {
		return this.jwtToken;
	}
}
