package com.teamrocket.sys3studentservice.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StudentDto {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Double credits;

    private List<BookDto> books;

}
