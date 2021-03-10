package com.example.demo.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ForgotPassword;
import com.example.demo.entity.JwtRequest;
import com.example.demo.entity.JwtResponse;
import com.example.demo.entity.UpdatePassword;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.MyUserDetailsService;
import com.example.demo.service.OtpService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtility;

@RestController
public class UserController
{
	private UserService userService;
	private JwtUtility jwtUtility;
	private AuthenticationManager authenticationManager;
	private MyUserDetailsService myUserDetailsService; //UserService
	private PasswordEncoder encoder;
	private OtpService otpService;
	
	@Autowired
	public UserController(UserService userService, JwtUtility jwtUtility, AuthenticationManager authenticationManager,
			MyUserDetailsService myUserDetailsService, PasswordEncoder encoder, OtpService otpService)
	{
		super();
		this.userService = userService;
		this.jwtUtility = jwtUtility;
		this.authenticationManager = authenticationManager;
		this.myUserDetailsService = myUserDetailsService;
		this.encoder = encoder;
		this.otpService = otpService;
	}

	@PostMapping("/signUp/{otp}")
	public JwtResponse createUser(@RequestBody User user,@PathVariable String otp) throws MessagingException
	{
		if(otpService.validateOtp(otp,user.getEmail()))
		{
			if(userService.findByUserEmail(user.getEmail()) != null)
				throw new UserNotFoundException("User already exists");
			user.setPassword(encoder.encode(user.getPassword()));
			userService.createUser(user);
			final UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());
			final String token = jwtUtility.generateToken(userDetails);
			return new JwtResponse(token);
		}
		else
			throw new UserNotFoundException("Wrong Otp");
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers()
	{
		return userService.showAllUsers();
	}
	
	@GetMapping("/findByEmail/{email}")
	public ResponseEntity<User> findByEmail(@PathVariable String email)
	{
		return userService.findByUserEmail(email);
	}
	
	@PutMapping("/updateUser")
	public void updateUser(@RequestBody User user)
	{
		User existingUser = userService.findById(user.getId()).getBody();
		if(encoder.matches(user.getPassword(), existingUser.getPassword()))
		{
			user.setPassword(encoder.encode(user.getPassword()));
			userService.updateUser(user);
		}
		else
			throw new UserNotFoundException("Existing password is incorrect");
	}
	
	@PutMapping("/updatePassword")
	public void updatePassword(@RequestHeader("Authorization") String auth, @RequestBody UpdatePassword updatePassword)
	{
		String email = jwtUtility.getUsernameFromToken(auth.substring(7));
		User user = userService.findByUserEmail(email).getBody();
		if(updatePassword.getOldPassword() != null && encoder.matches(updatePassword.getOldPassword(), user.getPassword()))
		{
			if(updatePassword.getNewPassword1().equals(updatePassword.getNewPassword2()))
			{
				user.setPassword(encoder.encode(updatePassword.getNewPassword1()));
				userService.updateUser(user);
			}
			else
				throw new UserNotFoundException("New passwords do not match");
		}
		else
			throw new UserNotFoundException("Old password is incorrect");
	}
	
	@PutMapping("/forgotPassword/{otp}")
	public void forgetPassword(@RequestBody ForgotPassword forgotPassword,@PathVariable String otp)
	{
		if(userService.findByUserEmail(forgotPassword.getEmail()).getBody() == null)
			throw new UserNotFoundException("Invalid Email");
		if(otpService.validateOtp(otp,forgotPassword.getEmail()))
		{
			User user = userService.findByUserEmail(forgotPassword.getEmail()).getBody();
			if(forgotPassword.getNewPassword1().equals(forgotPassword.getNewPassword2()))
			{
				user.setPassword(encoder.encode(forgotPassword.getNewPassword1()));
				userService.updateUser(user);
			}
			else
				throw new UserNotFoundException("New passwords do not match");
		}
		else
			throw new UserNotFoundException("Wrong otp");
	}
	
	@PostMapping("/login")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception
	{
		try
		{
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							jwtRequest.getUsername(), jwtRequest.getPassword()));
		}
		catch(BadCredentialsException e)
		{
			throw new Exception("INVALID CREDENTIALS",e);
		}
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		final String token = jwtUtility.generateToken(userDetails);
		return new JwtResponse(token);
	}
	
}
