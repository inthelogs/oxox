package com.example.demo.entity;

public class JwtResponse 
{
	private String jwtToken;

	public JwtResponse() {
		super();
	}

	public JwtResponse(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	
}
