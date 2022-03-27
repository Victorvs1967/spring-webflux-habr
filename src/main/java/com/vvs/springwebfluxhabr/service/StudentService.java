package com.vvs.springwebfluxhabr.service;

import com.vvs.springwebfluxhabr.model.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

  Flux<Student> findStudentsByName(String name);
  Mono<Student> findStudentById(String id);
  Mono<Student> addNewStudent(Student student);
  Mono<Student> updateStudent(String id, Student student);
  Mono<Void> deleteStudent(Student student);
}
