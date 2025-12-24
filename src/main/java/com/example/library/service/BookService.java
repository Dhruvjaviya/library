package com.example.library.service;

import com.example.library.entity.Books;
import java.util.List;

public interface BookService {

    List<Books> getAllBooks();

    Books addBook(Books book);

    Books getBookByName(String title);

    void deleteBook(Integer id);

    Books updateBook(Integer id, Books book);
}
