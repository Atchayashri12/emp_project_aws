package com.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.app.entity.CourseDetail;
 
public interface CourseRepository extends JpaRepository<CourseDetail, Long> {
}
 
