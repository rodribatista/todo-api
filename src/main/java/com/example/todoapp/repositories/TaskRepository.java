package com.example.todoapp.repositories;

import com.example.todoapp.models.Task;
import com.example.todoapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  Set<Task> findAllByUser(User user);

  @Transactional
  @Modifying
  @Query("UPDATE Task task SET task.isDone=:isDone, task.completedAt=:completedAt WHERE task.id=:id")
  void updateTaskDone(
    @Param("id") Long id,
    @Param("isDone") boolean isDone,
    @Param("completedAt") LocalDateTime completedAt
  );

}
