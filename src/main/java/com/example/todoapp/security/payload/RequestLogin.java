package com.example.todoapp.security.payload;

import lombok.Getter;

@Getter
public class RequestLogin {

  private String email;
  private String password;

}
