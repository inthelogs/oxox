package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserResponse;

@ControllerAdvice
public class UserExceptionHandler 
{
	@ExceptionHandler
	public ResponseEntity<UserResponse> handleException(UserNotFoundException unfe)
	{
		return new ResponseEntity<UserResponse>(
				new UserResponse(HttpStatus.NOT_FOUND.value(),unfe.getMsg(),System.currentTimeMillis()),HttpStatus.NOT_FOUND
				);
	}
}
