package com.teamrocket.sys3studentservice.repository;

import com.teamrocket.sys3studentservice.entity.Book;
import com.teamrocket.sys3studentservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByBooks(Book bookId);
}
