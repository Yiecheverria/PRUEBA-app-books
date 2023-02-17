package com.distribuida.servicios;
import com.distribuida.db.Books;

import java.util.List;
import java.util.Optional;
public interface BooksRepository {
    List<Books> findAll( );
    Optional<Books> findById(Integer id);
    void insert(Books book);
    void update( Books book);
    void delete( Integer id );
}
