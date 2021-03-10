package com.example.demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = -3204705332368137965L;
	private String msg;
	
	public UserNotFoundException(String msg)
	{
		super();
		this.msg = msg;
	}
}
