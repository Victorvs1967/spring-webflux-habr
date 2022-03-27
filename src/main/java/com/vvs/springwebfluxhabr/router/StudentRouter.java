package com.vvs.springwebfluxhabr.router;

import com.vvs.springwebfluxhabr.handler.StudentHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class StudentRouter {
  
  @Bean
  public RouterFunction<ServerResponse> studentRouterFunction(StudentHandler studentHandler) {
    return RouterFunctions
      .route(GET("/students/{id}").and(accept(APPLICATION_JSON)), studentHandler::getStudent)
      .andRoute(GET("/students").and(accept(APPLICATION_JSON)), studentHandler::listStudents)
      .andRoute(POST("/students").and(accept(APPLICATION_JSON)), studentHandler::addNewStudent)
      .andRoute(PUT("/students/{id}").and(accept(APPLICATION_JSON)), studentHandler::updateStudent)
      .andRoute(DELETE("/students/{id}").and(accept(APPLICATION_JSON)), studentHandler::deleteStudent);

  }
}
