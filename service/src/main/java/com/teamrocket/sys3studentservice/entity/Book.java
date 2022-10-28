package com.teamrocket.sys3studentservice.entity;

import static java.util.Objects.isNull;

import com.teamrocket.sys3studentservice.dto.BookDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Book {

    @Id
    private Long id;

    public static Book fromDto(BookDto bookDto) {
        return Book.builder()
            .id(bookDto.getId())
            .build();
    }

    public static List<Book> fromList(List<BookDto> bookDtos) {
        if (isNull(bookDtos)) {
            return new ArrayList<>();
        }
        return bookDtos
            .stream()
            .map(Book::fromDto)
            .collect(Collectors.toList());
    }
}
