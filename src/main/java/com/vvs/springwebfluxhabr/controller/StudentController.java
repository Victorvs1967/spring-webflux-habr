package com.vvs.springwebfluxhabr.controller;

import com.vvs.springwebfluxhabr.model.Student;
import com.vvs.springwebfluxhabr.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// @RestController
@RequestMapping("/students")
public class StudentController {
  
  @Autowired
  private StudentService studentService;

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Student>> getStudent(@PathVariable String id) {
    return studentService.findStudentById(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<Student> listStudents(@RequestParam(name = "name", required = false) String name) {
    return studentService.findStudentsByName(name);
  }

  @PostMapping
  public Mono<Student> addNewStudent(@RequestBody Student student) {
    return studentService.addNewStudent(student);
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Student>> updateStudent(@PathVariable String id, @RequestBody Student student) {
    return studentService.updateStudent(id, student)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable String id) {
    return studentService.findStudentById(id)
      .flatMap(s -> studentService.deleteStudent(s)
        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}