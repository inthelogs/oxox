package com.example.demo.entity;

import lombok.Getter;

@Getter
public class SignUpMail 
{
	private final String to;
    private final String content;
    private String subject = "Welcome";

    public SignUpMail(String to, String content)
    {
        this.to = to;
        this.content = content;
    }
}
