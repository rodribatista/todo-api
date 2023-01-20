package com.example.todoapp.configuration;

import com.example.todoapp.security.jwt.AuthEntryPoint;
import com.example.todoapp.security.jwt.RequestFilter;
import com.example.todoapp.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPoint unauthorizedHandler;

  @Bean
  public RequestFilter authenticationJwtTokenFilter() {
    return new RequestFilter();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().
      exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().
      sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
      authorizeRequests().
        antMatchers("/api/auth/**").permitAll().
        antMatchers("/v2/api-docs", "configuration/**", "/swagger*/**", "/webjars/**").permitAll().
        anyRequest().authenticated();
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
  }

}
