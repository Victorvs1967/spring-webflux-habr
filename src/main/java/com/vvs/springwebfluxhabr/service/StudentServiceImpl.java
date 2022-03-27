package com.vvs.springwebfluxhabr.service;

import com.vvs.springwebfluxhabr.model.Student;
import com.vvs.springwebfluxhabr.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceImpl implements StudentService  {

  @Autowired
  private StudentRepository studentRepository;

  @Override
  public Flux<Student> findStudentsByName(String name) {
    return name != null ? studentRepository.findByName(name) : studentRepository.findAll();
  }

  @Override
  public Mono<Student> findStudentById(String id) {
    return studentRepository.findById(id);
  }

  @Override
  public Mono<Student> addNewStudent(Student student) {
    return studentRepository.save(student);
  }

  @Override
  public Mono<Student> updateStudent(String id, Student student) {
    return studentRepository.findById(id)
      .flatMap(s -> {
        student.setId(s.getId());
        return studentRepository.save(student);
      });
  }

  @Override
  public Mono<Void> deleteStudent(Student student) {
    return studentRepository.delete(student);
  }
 
}
