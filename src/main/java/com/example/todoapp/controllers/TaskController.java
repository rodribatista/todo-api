package com.example.todoapp.controllers;

import com.example.todoapp.exceptions.*;
import com.example.todoapp.models.Task;
import com.example.todoapp.payload.RequestTask;
import com.example.todoapp.services.TaskService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@AllArgsConstructor
@RestController
public class TaskController {

  private final TaskService taskService;

  @GetMapping("/api/tasks/{id}")
  public ResponseEntity<Task> get(
    @RequestHeader (HttpHeaders.AUTHORIZATION) String token,
    @PathVariable("id") Long id) throws NotFoundException, UnauthorizedException {
    String jwt = token.replace("Bearer", "").trim();
    return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(id, jwt));
  }

  @GetMapping("/api/tasks")
  public ResponseEntity<Set<Task>> getAll(
    @RequestHeader (HttpHeaders.AUTHORIZATION) String token) throws NoContentException, NotFoundException {
    String jwt = token.replace("Bearer", "").trim();
    return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks(jwt));
  }

  @PostMapping("/api/tasks")
  public ResponseEntity<Task> create(
    @RequestHeader (HttpHeaders.AUTHORIZATION) String token,
    @RequestBody @NotNull @Validated RequestTask task) throws BadRequestException, NotFoundException {
    String jwt = token.replace("Bearer", "").trim();
    return ResponseEntity.status((HttpStatus.CREATED)).body(taskService.createTask(task, jwt));
  }

  @PutMapping("/api/tasks/{id}")
  public ResponseEntity<Task> updateIsDone(
    @RequestHeader (HttpHeaders.AUTHORIZATION) String token,
    @PathVariable("id") Long id) throws NotFoundException, UnauthorizedException {
    String jwt = token.replace("Bearer", "").trim();
    return ResponseEntity.status((HttpStatus.OK)).body(taskService.updateTaskDone(id, jwt));
  }

  @DeleteMapping("/api/tasks/{id}")
  public ResponseEntity<String> remove(
    @RequestHeader (HttpHeaders.AUTHORIZATION) String token,
    @PathVariable("id") Long id) throws ConflictException, NotFoundException, UnauthorizedException {
    String jwt = token.replace("Bearer", "").trim();
    taskService.removeTask(id, jwt);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tarea ID: " + id + " eliminada correctamente");
  }

}
