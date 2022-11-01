package com.teamrocket.sys3studentservice.application.controller;

import com.teamrocket.sys3studentservice.dto.CourseDto;
import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<IDDto<Long>> createCourse(@RequestBody @Valid CourseDto courseDto) {
        IDDto<Long> id = courseService.createCourse(courseDto);
        return ResponseEntity.status(201).body(id);
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<IDDto<Long>> addCourseToStudent(@PathVariable("studentId") Long studentId, @RequestBody CourseDto courseDto) {
        IDDto<Long> id = courseService.addCourseToStudent(studentId, courseDto.getId());
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<IDDto<Long>> deleteCourse(@PathVariable("courseId") Long courseId) {
        IDDto<Long> id = courseService.deleteCourse(courseId);
        return ResponseEntity.ok(id);
    }

    @PutMapping
    public ResponseEntity<IDDto<Long>> updateCourse(@RequestBody CourseDto courseDto) {
        IDDto<Long> id = courseService.updateCourse(courseDto);
        return ResponseEntity.ok(id);
    }

    // Denne metode er et "getRecommentations" debug REST-endpoint
    /*@GetMapping("/{bookId}")
    public ResponseEntity<List<IDDto<Long>>> getByBookId(@PathVariable("bookId") Long bookId){
        List<IDDto<Long>> recommendations = courseService.getRecommendations(bookId);
        return ResponseEntity.status(200).body(recommendations);
    }*/
}
