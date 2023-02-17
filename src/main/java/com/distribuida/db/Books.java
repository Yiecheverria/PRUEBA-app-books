package com.distribuida.db;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Books {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String isbn;
    private String title;
    private String author;
    private Double price;
}
