package com.example.todoapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptions.class);

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<String> badRequest(BadRequestException exception) {
    log.error(HttpStatus.BAD_REQUEST + " - " + exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<String> conflict(ConflictException exception) {
    log.error(HttpStatus.CONFLICT + " - " + exception.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
  }

  @ExceptionHandler(NoContentException.class)
  public ResponseEntity<String> noContent(NoContentException exception) {
    log.error(HttpStatus.NO_CONTENT + " - " + exception.getMessage());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> notFound(NotFoundException exception) {
    log.error(HttpStatus.NOT_FOUND + " - " + exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<String> unauthorized(UnauthorizedException exception) {
    log.error(HttpStatus.UNAUTHORIZED + " - " + exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
  }

}
