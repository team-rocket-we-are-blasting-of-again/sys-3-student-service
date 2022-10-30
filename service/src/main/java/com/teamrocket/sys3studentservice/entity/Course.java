package com.teamrocket.sys3studentservice.entity;

import com.teamrocket.sys3studentservice.dto.CourseDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "course_books",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<Book> books = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "course_students",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    private List<Student> students = new ArrayList<>();

    public static Course fromDto(CourseDto courseDto) {
        return Course.builder()
                .books(Book.fromList(courseDto.getBooks()))
                .students(Student.fromList(courseDto.getStudents()))
                .build();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
    public void addStudent(Student student) {this.students.add(student);}

    public void addBooks(List<Book> existingBooks) {
        for (Book book: existingBooks
             ) {
            this.addBook(book);
        }
    }
}
