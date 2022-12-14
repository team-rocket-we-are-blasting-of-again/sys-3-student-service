package com.teamrocket.sys3studentservice.entity;

import com.teamrocket.sys3studentservice.dto.StudentDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.isNull;

@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private Double credits;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "students_books",
        joinColumns = {@JoinColumn(name = "student_id")},
        inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<Book> books = new ArrayList<>();

    public static List<Student> fromList(List<StudentDto> students) {
        if (isNull(students)) {
            return new ArrayList<>();
        }
        return students
                .stream()
                .map(Student::fromDto)
                .collect(Collectors.toList());
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public static Student fromDto(StudentDto studentDto) {
        return Student.builder()
            .name(studentDto.getName())
            .credits(studentDto.getCredits())
            .books(Book.fromList(studentDto.getBooks()))
            .build();

    }

}
