package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotPassword
{
	String email;
	String newPassword1;
	String newPassword2;
}
