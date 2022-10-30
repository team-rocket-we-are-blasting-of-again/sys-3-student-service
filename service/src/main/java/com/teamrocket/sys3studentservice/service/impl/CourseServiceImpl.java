package com.teamrocket.sys3studentservice.service.impl;

import com.teamrocket.sys3studentservice.dto.BookDto;
import com.teamrocket.sys3studentservice.dto.CourseDto;
import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.dto.StudentDto;
import com.teamrocket.sys3studentservice.entity.Book;
import com.teamrocket.sys3studentservice.entity.Course;
import com.teamrocket.sys3studentservice.entity.Student;
import com.teamrocket.sys3studentservice.exception.DuplicateBooksException;
import com.teamrocket.sys3studentservice.exception.NotFoundException;
import com.teamrocket.sys3studentservice.repository.BookRepository;
import com.teamrocket.sys3studentservice.repository.CourseRepository;
import com.teamrocket.sys3studentservice.repository.StudentRepository;
import com.teamrocket.sys3studentservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;

    @Override
    public IDDto<Long> createCourse(CourseDto courseDto) {
        List<Book> existingBooks = checkBooks(courseDto);
        Course course = courseRepository.save(Course.fromDto(courseDto));
        course.addBooks(existingBooks);
        return new IDDto<>(course.getId());
    }

    // Checks if given books exist, if not, add to persisted object, else add later
    // Makes sure that given books are merged if exists, and persisted if not exist
    private List<Book> checkBooks(CourseDto courseDto) {
        List<Long> ids = new ArrayList<>();
        for (BookDto dto: courseDto.getBooks()
             ) {
            ids.add(dto.getId());
        }
        List<Book> existingBooks = bookRepository.findAllById(ids);
        List<BookDto> filtered = new ArrayList<>();
        for (BookDto dto : courseDto.getBooks()
             ) {
            if(!existingBooks.stream().map(book -> {
                return book.getId();
            }).toList().contains(dto.getId())){
                filtered.add(dto);
            }
        }
        courseDto.setBooks(filtered);
        return existingBooks;
    }

    @Override
    public IDDto<Long> addCourseToStudent(Long studentId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(NotFoundException::new);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(NotFoundException::new);
        try {
            course.addStudent(student);
            return new IDDto<>(course.getId());
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<IDDto<Long>> getRecommendations(Long bookId) {
        List<Course> courses = getCoursesWithBook(bookId);
        List<IDDto<Long>> recommendations = new ArrayList<>();
        for (Course course : courses
             ) {
            for (Book book : course.getBooks()
                 ) {
                if(book.getId() != bookId){
                    recommendations.add(new IDDto<>(book.getId()));
                }
            }
        }
        return recommendations;
    }

    @Override
    public List<Course> getCoursesWithBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(NotFoundException::new);
        List<Course> courses = courseRepository.findAllByBooks(book);
        return courses;
    }

    @Override
    public IDDto<Long> deleteCourse(Long id) {
        courseRepository.deleteById(id);
        return new IDDto<>(id);
    }

    @Override
    public IDDto<Long> updateCourse(CourseDto courseDto) {
        Course course = courseRepository.save(Course.fromDto(courseDto));
        return new IDDto<>(course.getId());
    }
}
