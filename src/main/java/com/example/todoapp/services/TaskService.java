package com.example.todoapp.services;

import com.example.todoapp.exceptions.*;
import com.example.todoapp.models.Task;
import com.example.todoapp.payload.RequestTask;
import com.example.todoapp.repositories.TaskRepository;
import com.example.todoapp.repositories.UserRepository;
import com.example.todoapp.security.jwt.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final TokenUtil tokenUtil;

  public Task getTaskById(Long id, String jwt) throws NotFoundException, UnauthorizedException {
    if (isTaskAccesible(id, jwt)) {
      return taskRepository.findById(id).orElse(null);
    }
    return null;
  }

  public Set<Task> getAllTasks(String jwt) throws NoContentException, NotFoundException {
    String userMail = tokenUtil.getUserNameFromToken(jwt);
    var user = userRepository.findByEmail(userMail).orElseThrow(
      () -> new NotFoundException("No existe un usuario registrado con el mail: " + userMail)
    );
    var taskList = taskRepository.findAllByUser(user);
    if (taskList.isEmpty())
      throw new NoContentException("No hay tareas para mostrar");
    return taskList;
  }

  public Task createTask(RequestTask requestTask, String jwt) throws BadRequestException, NotFoundException {
    String userMail = tokenUtil.getUserNameFromToken(jwt);
    var newTask = new Task(
      null,
      requestTask.getDescription(),
      LocalDateTime.now(),
      false,
      null,
      userRepository.findByEmail(userMail).orElseThrow(
        () -> new NotFoundException("No existe un usuario registrado con el mail: " + userMail)
      )
    );
    return taskRepository.save(newTask);
  }

  public Task updateTaskDone(Long id, String jwt) throws NotFoundException, UnauthorizedException {
    if (isTaskAccesible(id, jwt)) {
      var task = taskRepository.findById(id).orElse(null);
      if(task.isDone()) {
        taskRepository.updateTaskDone(id, false, null);
      } else {
        taskRepository.updateTaskDone(id, true, LocalDateTime.now());
      }
    }
    return taskRepository.findById(id).orElse(null);
  }

  public void removeTask(Long id, String jwt) throws NotFoundException, ConflictException, UnauthorizedException {
    if (isTaskAccesible(id, jwt)) {
      try {
        taskRepository.deleteById(id);
      } catch (Exception e) {
        throw new ConflictException(e.getMessage());
      }
    }
  }

  private boolean isTaskAccesible(Long id, String jwt) throws NotFoundException, UnauthorizedException {
    String userMail = tokenUtil.getUserNameFromToken(jwt);
    var task = taskRepository.findById(id).orElseThrow(
      () -> new NotFoundException("No existe una tarea con el ID: " + id)
    );
    var user = userRepository.findByEmail(userMail).orElseThrow(
      () -> new NotFoundException("No existe un usuario registrado con el mail: " + userMail)
    );
    if (task.getUser() != user) {
      throw new UnauthorizedException("El usuario no tiene accesos para la tarea ID: " + id);
    }
    return true;
  }

}
