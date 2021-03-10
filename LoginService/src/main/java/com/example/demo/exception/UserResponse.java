package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse 
{
	private int statusCode;
	private String msg;
	private long time;
	public UserResponse(int statusCode)
	{
		super();
		this.statusCode = statusCode;
	}
}
