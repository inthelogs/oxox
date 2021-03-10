package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository(value = "userRepo")
public interface UserRepo extends JpaRepository<User, Integer>
{
	@Query
	public Optional<User> findByEmail(String email);
}
