package com.example.todoapp.controllers;

import com.example.todoapp.models.User;
import com.example.todoapp.repositories.UserRepository;
import com.example.todoapp.security.jwt.TokenUtil;
import com.example.todoapp.security.payload.JwtResponse;
import com.example.todoapp.security.payload.MessageResponse;
import com.example.todoapp.security.payload.RequestLogin;
import com.example.todoapp.security.payload.RequestSignup;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@CrossOrigin
@AllArgsConstructor
@RestController
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenUtil tokenUtil;

  @PostMapping("/api/auth/login")
  public ResponseEntity<JwtResponse> login(@RequestBody RequestLogin request) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwtToken = tokenUtil.generateToken(authentication);
    return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(jwtToken));
  }

  @PostMapping("/api/auth/signup")
  public ResponseEntity<MessageResponse> signup(@RequestBody RequestSignup request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(new MessageResponse("Ya existe un usuario registrado con este email."));
    }
    userRepository.save(new User(
      null,
      request.getFirstName(),
      request.getLastName(),
      request.getEmail(),
      passwordEncoder.encode(request.getPassword()),
      new HashSet<>()
    ));
    return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Usuario registrado correctamente"));
  }

}
