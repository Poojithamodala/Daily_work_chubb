package com.MongoDemo.MongoDemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.MongoDemo.MongoDemo.model.Student;

public interface StudentRepo extends MongoRepository<Student, Integer>{

}
