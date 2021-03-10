package com.example.demo.service;

public interface OtpService 
{
	public int generateOTP(String key);	 
	public String getOtp(String key);
	public void clearOTP(String key);
	public Boolean validateOtp(String otp,String key);
}
