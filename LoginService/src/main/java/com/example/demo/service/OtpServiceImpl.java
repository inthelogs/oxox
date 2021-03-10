package com.example.demo.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service(value = "otpService")
public class OtpServiceImpl implements OtpService
{
	private static final Integer EXPIRE_MINS = 4;
    private LoadingCache<String, String> otpCache;
    @Autowired
    private PasswordEncoder encoder;
    public OtpServiceImpl()
    {
		super();
		otpCache = CacheBuilder.newBuilder().
		expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
		.build(new CacheLoader<String, String>() {
						public String load(String key)
						{
						   return null;
						}
		  			});
    }

	public int generateOTP(String key)
	{
		Random random = new Random();
		Integer otp = 100000 + random.nextInt(900000);
		otpCache.put(key, encoder.encode(otp.toString()));
		return otp;
	}
	 
	public String getOtp(String key)
	{ 
		try
		{
		 return otpCache.get(key); 
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public void clearOTP(String key)
	{ 
		otpCache.invalidate(key);
	}
	
	public Boolean validateOtp(String otp,String key)
	{
		if(otp != null)
		{
			String serverOtp = getOtp(key);
			if(serverOtp != null)
			{
				if(encoder.matches(otp, serverOtp))
				{
					clearOTP(key);
					return true;
				}
				else
					return false;
			}
			else
				return false;
		}
		else
			return false;
	}
}
