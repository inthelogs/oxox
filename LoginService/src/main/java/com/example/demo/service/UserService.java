package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.entity.User;

public interface UserService 
{
	public ResponseEntity<List<User>> showAllUsers();
	public ResponseEntity<User> findByUserEmail(String email);
	public void updateUser(User user);
	public void createUser(User user);
	public ResponseEntity<User> findById(int id);
}
