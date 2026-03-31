package com.app.service;

import com.app.entity.CourseDetail;
import com.app.entity.LessonDetail;
import com.app.repository.CourseRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public CourseDetail createCourse(final CourseDetail course) {
        Objects.requireNonNull(course, "course must not be null");

        // Build a fresh aggregate to avoid mutating the input
        CourseDetail toSave = new CourseDetail();
        toSave.setTitle(course.getTitle());
        toSave.setDescription(course.getDescription());

        if (course.getLessons() != null && !course.getLessons().isEmpty()) {
            List<LessonDetail> attached = new ArrayList<>(course.getLessons().size());
            for (LessonDetail src : course.getLessons()) {
                LessonDetail l = new LessonDetail();
                l.setTitle(src.getTitle());
                l.setContent(src.getContent());
                l.setCourse(toSave); // maintain bidirectional relationship
                attached.add(l);
            }
            toSave.setLessons(attached);
        }

        CourseDetail saved = repository.save(toSave);
        log.info("Created course id={}", saved.getId());
        return saved;
    }

    
    public List<CourseDetail> getAllCourses() {
        // No exception is necessary for empty results; just return empty list
        return repository.findAll();
    }

    
    public CourseDetail getCourse(final Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public CourseDetail updateCourse(final Long id, final CourseDetail updated) {
        Objects.requireNonNull(updated, "updated course must not be null");

        CourseDetail course = getCourse(id);

        // Update scalar fields
        course.setTitle(updated.getTitle());
        course.setDescription(updated.getDescription());

        // Replace lessons in a safe way (avoid mutating 'updated')
        course.getLessons().clear();
        if (updated.getLessons() != null && !updated.getLessons().isEmpty()) {
            for (LessonDetail src : updated.getLessons()) {
                LessonDetail l = new LessonDetail();
                l.setTitle(src.getTitle());
                l.setContent(src.getContent());
                l.setCourse(course);
                course.getLessons().add(l);
            }
        }

        CourseDetail saved = repository.save(course);
        log.info("Updated course id={}", saved.getId());
        return saved;
    }

    
    @Transactional
    public void deleteCourse(final Long id) throws Exception {
        if (!repository.existsById(id)) {
            log.warn("Delete requested for non-existent course id={}", id);
            throw new Exception("Course not found: id=" + id);
        }
        repository.deleteById(id);
        log.info("Deleted course id={}", id);
    }
}