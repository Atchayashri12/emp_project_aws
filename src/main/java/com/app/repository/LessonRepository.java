package com.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.app.entity.LessonDetail;
 
public interface LessonRepository extends JpaRepository<LessonDetail, Long> {
}
 
