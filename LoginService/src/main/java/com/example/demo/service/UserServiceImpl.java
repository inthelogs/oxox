package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repo.UserRepo;

@Service(value = "userService")
@EnableTransactionManagement
public class UserServiceImpl implements UserService
{
	private UserRepo userRepo;
	@Autowired
	public UserServiceImpl(UserRepo userRepo)
	{
		this.userRepo = userRepo;
	}
	
	@Override
	public ResponseEntity<List<User>> showAllUsers()
	{
		List<User> users = new ArrayList<>();
		users = userRepo.findAll();
		return ResponseEntity.ok().body(users);
	}

	@Override
	public ResponseEntity<User> findByUserEmail(String email) 
	{
		if(userRepo.findByEmail(email).isPresent())
		{
			User fetchUser = userRepo.findByEmail(email).get();
			return ResponseEntity.ok().body(fetchUser);
		}
		else
			return null;
	}

	@Override
	public void updateUser(User user)
	{
		if((userRepo.findById(user.getId())).isPresent())
			userRepo.save(user);
		else
			throw new UserNotFoundException("User does not exist");
	}

	@Override
	public void createUser(User user)
	{
		userRepo.save(user);
	}

	@Override
	public ResponseEntity<User> findById(int id) 
	{
		if(userRepo.findById(id).isPresent())
			return ResponseEntity.ok().body(userRepo.findById(id).get());
		else
			throw new UserNotFoundException("User does not exist");
	}
}