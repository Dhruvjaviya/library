package com.example.library.service.impl;

import com.example.library.entity.Books;
import com.example.library.exception.BadRequestException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.util.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseEntity<Object> getAllBooks() {
        try {
            return ApiResponse.success(bookRepository.findAll(), "Books fetched successfully");
        } catch (Exception e) {
            throw new BadRequestException("Failed to fetch books");
        }
    }

    @Override
    public ResponseEntity<Object> addBook(Books book) {
        try {
            return ApiResponse.created(bookRepository.save(book), "Book added successfully");
        } catch (Exception e) {
            throw new BadRequestException("Failed to add book");
        }
    }

    @Override
    public ResponseEntity<Object> getBookByName(String title) {
        try {
            Books book = bookRepository.findByTitle(title);
            if (book == null) {
                throw new ResourceNotFoundException("Book not found with title: " + title);
            }
            return ApiResponse.success(book, "Book found");
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateBook(Integer id, Books book) {
        try {
            Books existingBook = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setGenre(book.getGenre());
            existingBook.setIsbn(book.getIsbn());
            existingBook.setPublisher(book.getPublisher());
            existingBook.setPublicationYear(book.getPublicationYear());
            existingBook.setPrice(book.getPrice());
            existingBook.setStockQuantity(book.getStockQuantity());

            return ApiResponse.success(bookRepository.save(existingBook), "Book updated successfully");

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> deleteBook(Integer id) {
        try {
            Books book = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

            bookRepository.delete(book);
            return ApiResponse.success(null, "Book deleted successfully");

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
