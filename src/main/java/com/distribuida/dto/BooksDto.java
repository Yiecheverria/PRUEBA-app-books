package com.distribuida.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksDto {
    private Integer id;
    private String isbn;
    private String title;
    private String author;
    private Double price;

    private String authorName;
}