package com.example.library.service;

import com.example.library.entity.Books;
import org.springframework.http.ResponseEntity;

public interface BookService {

    ResponseEntity<Object> getAllBooks();

    ResponseEntity<Object> addBook(Books book);

    ResponseEntity<Object> getBookByName(String title);

    ResponseEntity<Object> updateBook(Integer id, Books book);

    ResponseEntity<Object> deleteBook(Integer id);
    ResponseEntity<Object> getBooksWithPagination(
            int page,
            int size,
            String sortBy,
            String sortDir
    );
}
