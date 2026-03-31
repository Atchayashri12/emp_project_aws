package com.app.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDetail {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @NotBlank(message = "Lesson title is required")
    private String title;
 
    private String content;
 
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private CourseDetail course;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CourseDetail getCourse() {
		return course;
	}

	public void setCourse(CourseDetail course) {
		this.course = course;
	}

	
}
 