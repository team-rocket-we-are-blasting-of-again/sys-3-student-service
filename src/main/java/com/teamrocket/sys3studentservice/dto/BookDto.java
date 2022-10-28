package com.teamrocket.sys3studentservice.dto;

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
public class BookDto {

    @NotNull
    private Long id;

    @NotNull
    private Double price;

}
