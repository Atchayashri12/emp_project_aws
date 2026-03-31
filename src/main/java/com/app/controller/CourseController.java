package com.app.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.entity.CourseDetail;
import com.app.service.CourseService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CourseController {

	@Autowired
	private CourseService service;

	@PostMapping
	public CourseDetail create(@Valid @RequestBody CourseDetail course) {
		return service.createCourse(course);
	}

	@GetMapping
	public List<CourseDetail> getAll() {
		return service.getAllCourses();
	}

	@GetMapping("/{id}")
	public CourseDetail getById(@PathVariable Long id) {
		return service.getCourse(id);
	}

	@PutMapping("/{id}")
	public CourseDetail update(@PathVariable Long id, @Valid @RequestBody CourseDetail course) {
		return service.updateCourse(id, course);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws Exception {
		service.deleteCourse(id);
	}
}
