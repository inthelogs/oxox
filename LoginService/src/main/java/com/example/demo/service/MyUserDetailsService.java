package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
@Service
public class MyUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException 
	{
		User user = userRepo.findByEmail(userEmail).get();
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),new ArrayList<>());
	}

}
