package com.vvs.springwebfluxhabr.repository;

import com.vvs.springwebfluxhabr.model.Student;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
	Flux<Student> findByName(String name);
}
