package com.example.demo.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SignUpMail;
import com.example.demo.service.MailSenderService;
import com.example.demo.service.OtpService;

@RestController
public class OtpController 
{
	@Autowired
	public OtpService otpService;

	@Autowired
	private MailSenderService senderService;

	@GetMapping("/sendOtp/{email}")
	public Boolean generateOTP(@PathVariable String email) throws MessagingException
	{
		int otp = otpService.generateOTP(email);
		String content = "<html>" +
                "<body>" +
                "<p>Hello</p>" +
                "<p>You have been successfully registered on <strong>________</strong> portal!</p>" +
                "<p><strong>Your otp is: </strong>"+otp+"</p>"+
                "<p>Regards,</p>"+
                "<p>_______</p>"+
                "</body>" +
                "</html>";
		senderService.sendHTMLMail(new SignUpMail(email, content));
		return true;
	}

	@GetMapping("/validateOtp/{otp}/{key}")
	public Boolean validateOtp(@PathVariable String otp,@PathVariable String key)
	{
			return otpService.validateOtp(otp,key);
	}
}
