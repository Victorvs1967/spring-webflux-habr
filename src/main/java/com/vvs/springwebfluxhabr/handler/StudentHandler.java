package com.vvs.springwebfluxhabr.handler;

import com.vvs.springwebfluxhabr.model.Student;
import com.vvs.springwebfluxhabr.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class StudentHandler {

  @Autowired
  private StudentService studentService;

  public Mono<ServerResponse> getStudent(ServerRequest request) {
    return studentService.findStudentById(request.pathVariable("id"))
      .flatMap(student -> ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(student)))
      .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> listStudents(ServerRequest request) {
    String name = request.queryParam("name").orElse(null);
    return ServerResponse
      .ok()
      .contentType(APPLICATION_JSON)
      .body(studentService.findStudentsByName(name), Student.class);
  }

  public Mono<ServerResponse> addNewStudent(ServerRequest request) {
    return request.bodyToMono(Student.class)
      .flatMap(student -> ServerResponse
            .status(HttpStatus.OK)
            .contentType(APPLICATION_JSON)
        .body(studentService.addNewStudent(student), Student.class));
  }

  public Mono<ServerResponse> updateStudent(ServerRequest request) {
    String studentId = request.pathVariable("id");
    return request.bodyToMono(Student.class)
      .flatMap(student -> ServerResponse
        .status(HttpStatus.OK)
        .contentType(APPLICATION_JSON)
        .body(studentService.updateStudent(studentId, student), Student.class));
  }

  public Mono<ServerResponse> deleteStudent(ServerRequest request) {
    return studentService.findStudentById(request.pathVariable("id"))
      .flatMap(student -> ServerResponse
        .noContent()
        .build(studentService.deleteStudent(student)))
      .switchIfEmpty(ServerResponse.notFound().build());
  }

}
