package com.teamrocket.sys3studentservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CourseDto {
    private Long id;

    @NotNull
    private List<BookDto> books;

    private List<StudentDto> students;
}
