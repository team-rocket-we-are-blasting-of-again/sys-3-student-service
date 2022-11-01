package com.teamrocket.sys3studentservice.service;

import com.teamrocket.sys3studentservice.dto.CourseDto;
import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.entity.Course;

import java.util.List;

public interface CourseService {
    IDDto<Long> createCourse(CourseDto courseDto);

    IDDto<Long> addCourseToStudent(Long studentId, Long courseId);

    IDDto<Long> deleteCourse(Long id);

    IDDto<Long> updateCourse(CourseDto courseDto);

    List<IDDto<Long>> getRecommendations(Long bookId);

    List<Course> getCoursesWithBook(Long bookId);
}
