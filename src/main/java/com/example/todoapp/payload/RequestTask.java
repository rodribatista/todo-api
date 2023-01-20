package com.example.todoapp.payload;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class RequestTask {

  @Column(nullable = false)
  private String description;

}
