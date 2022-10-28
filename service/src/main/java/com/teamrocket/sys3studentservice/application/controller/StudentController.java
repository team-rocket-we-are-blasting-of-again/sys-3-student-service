package com.teamrocket.sys3studentservice.application.controller;

import com.teamrocket.sys3studentservice.dto.BookDto;
import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.dto.StudentDto;
import com.teamrocket.sys3studentservice.service.StudentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final KafkaTemplate<String, IDDto<Long>> kafkaTemplate;

    @PostMapping
    public ResponseEntity<IDDto<Long>> createStudent(@RequestBody @Valid StudentDto studentDto) {
        IDDto<Long> id = studentService.createStudent(studentDto);
        return ResponseEntity.status(201).body(id);
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<IDDto<Long>> addBookToStudent(@PathVariable("studentId") Long studentId, @RequestBody BookDto bookDto) {
        IDDto<Long> id = studentService.addBookToStudent(studentId, bookDto);
        // TODO: This should also be emitted from grpc
        kafkaTemplate.send("bookBought", id);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<IDDto<Long>> deleteStudent(@PathVariable("studentId") Long studentId) {
        IDDto<Long> id = studentService.deleteStudent(studentId);
        return ResponseEntity.ok(id);
    }

}
