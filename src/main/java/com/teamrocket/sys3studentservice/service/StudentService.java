package com.teamrocket.sys3studentservice.service;

import com.teamrocket.sys3studentservice.dto.BookDto;
import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.dto.StudentDto;

public interface StudentService {

    IDDto<Long> createStudent(StudentDto studentDto);

    IDDto<Long> addBookToStudent(Long studentId, BookDto bookDto);
    IDDto<Long> deleteStudent(Long id);

}
