package com.vvs.springwebfluxhabr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
  
  @Bean
  public MapReactiveUserDetailsService userDetailsService() {

    UserDetails user = User
      .withUsername("user")
      .password(passwordEncoder().encode("password"))
      .roles("USER")
      .build();

    UserDetails admin = User
      .withUsername("admin")
      .password(passwordEncoder().encode("Password1"))
      .roles("ADMIN")
      .build();

    return new MapReactiveUserDetailsService(user, admin);
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
      .csrf().disable()
      .formLogin().disable()
      .authorizeExchange()
      .pathMatchers(HttpMethod.POST).hasAuthority("ROLE_ADMIN")
      .pathMatchers(HttpMethod.PUT).hasAuthority("ROLE_ADMIN")
      .pathMatchers(HttpMethod.DELETE).hasAuthority("ROLE_ADMIN")
      .pathMatchers("/students/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
      .anyExchange().authenticated().and()
      .httpBasic().and()
      .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
