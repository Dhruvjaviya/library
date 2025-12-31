package com.example.library.dao;

import com.example.library.entity.Books;
import org.springframework.data.domain.*;

import java.util.List;

public interface BookDao {

    List<Books> findAll();

    Page<Books> findAll(Pageable pageable);

    Books findById(Integer id);

    Books findByTitle(String title);

    Books save(Books book);

    void deleteById(Integer id);

    boolean existsByIsbn(String isbn);
}
