package com.teamrocket.sys3studentservice.service.impl;

import com.teamrocket.sys3studentservice.dto.BookDto;
import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.dto.StudentDto;
import com.teamrocket.sys3studentservice.entity.Book;
import com.teamrocket.sys3studentservice.entity.Student;
import com.teamrocket.sys3studentservice.exception.DuplicateBooksException;
import com.teamrocket.sys3studentservice.exception.NotFoundException;
import com.teamrocket.sys3studentservice.repository.StudentRepository;
import com.teamrocket.sys3studentservice.service.StudentService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public IDDto<Long> createStudent(StudentDto studentDto) {
        Student student = studentRepository.save(Student.fromDto(studentDto));
        return new IDDto<>(student.getId());
    }

    @Override
    public IDDto<Long> addBookToStudent(Long studentId, BookDto bookDto) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(NotFoundException::new);
        try {
            student.addBook(Book.fromDto(bookDto));
            student.setCredits(student.getCredits() - bookDto.getPrice());
            return new IDDto<>(bookDto.getId());
        } catch (Exception e) {
            throw new DuplicateBooksException();
        }
    }

    @Override
    public IDDto<Long> deleteStudent(Long id) {
        studentRepository.deleteById(id);
        return new IDDto<>(id);
    }
}
