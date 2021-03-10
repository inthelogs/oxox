package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePassword 
{
	String oldPassword;
	String newPassword1;
	String newPassword2;
}
