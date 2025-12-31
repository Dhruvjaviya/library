package com.example.library.service.impl;

import com.example.library.dao.BookDao;
import com.example.library.entity.Books;
import com.example.library.exception.BadRequestException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.service.BookService;
import com.example.library.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public ResponseEntity<Object> getAllBooks() {
        return ApiResponse.success(bookDao.findAll(), "Books fetched successfully");
    }

    @Override
    public ResponseEntity<Object> addBook(Books book) {
        if (bookDao.existsByIsbn(book.getIsbn())) {
            throw new BadRequestException("Book with this ISBN already exists");
        }
        return ApiResponse.success(bookDao.save(book), "Book added successfully");
    }

    @Override
    public ResponseEntity<Object> getBookByName(String title) {
        Books book = bookDao.findByTitle(title);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found");
        }
        return ApiResponse.success(book, "Book found");
    }

    @Override
    public ResponseEntity<Object> updateBook(Integer id, Books book) {
        Books existing = bookDao.findById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Book not found");
        }

        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setGenre(book.getGenre());
        existing.setIsbn(book.getIsbn());
        existing.setPublisher(book.getPublisher());
        existing.setPublicationYear(book.getPublicationYear());
        existing.setPrice(book.getPrice());
        existing.setStockQuantity(book.getStockQuantity());

        return ApiResponse.success(bookDao.save(existing), "Book updated successfully");
    }

    @Override
    public ResponseEntity<Object> deleteBook(Integer id) {
        Books book = bookDao.findById(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found");
        }

        bookDao.deleteById(id);
        return ApiResponse.success(null, "Book deleted successfully");
    }

    @Override
    public ResponseEntity<Object> getBooksWithPagination(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Books> booksPage = bookDao.findAll(pageable);

        return ApiResponse.success(booksPage, "Books fetched successfully");
    }


}
