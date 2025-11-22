package com.MongoDemo.MongoDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.MongoDemo.MongoDemo.model.Student;
import com.MongoDemo.MongoDemo.repository.StudentRepo;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class StudentController {
	@Autowired
	private StudentRepo studentRepo;
	
	@PostMapping("/createStudent")
	public void createStudent(@RequestBody Student student) {
		studentRepo.save(student);
	}
	
	@GetMapping("/getStudents")
	public List<Student> getStudents() {
		return studentRepo.findAll();
	}
	
	@GetMapping("/getStudents/{id}")
	public Student getStudents(@PathVariable Integer id) {
		return studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Student not found!"));
	}
	
	@PutMapping("/updateStudent")
	public void updateStudent(@RequestBody Student student) {
		Student info=studentRepo.findById(student.getId()).orElseThrow(() -> new RuntimeException("Student not found!"));
		if(info!=null) {
			info.setName(student.getName());
			info.setAddress(student.getAddress());
			studentRepo.save(info);
		}
	}
	
	@DeleteMapping("/deleteStudent/{id}")
	public void deleteStudent(@PathVariable Integer id) {
		studentRepo.deleteById(id);
	}

}
