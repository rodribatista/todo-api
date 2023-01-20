package com.example.todoapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private boolean isDone;

  private LocalDateTime completedAt;

  @JoinColumn(nullable = false)
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JsonIgnore
  private User user;

}
