package com.example.todoapp.security.payload;

import lombok.Getter;

@Getter
public class RequestSignup {

  private String firstName;
  private String lastName;
  private String email;
  private String password;

}
