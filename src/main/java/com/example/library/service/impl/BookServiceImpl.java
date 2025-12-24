package com.example.library.service.impl;



import com.example.library.entity.Books;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Books addBook(Books book) {
        return bookRepository.save(book);
    }

    @Override
    public Books getBookByName(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
    @Override
    public Books updateBook(Integer id, Books book) {

        Books existingBook = bookRepository.findById(id).orElse(null);

        if (existingBook != null) {

            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setGenre(book.getGenre());
            existingBook.setIsbn(book.getIsbn());
            existingBook.setPublisher(book.getPublisher());
            existingBook.setPublicationYear(book.getPublicationYear());
            existingBook.setPrice(book.getPrice());
            existingBook.setStockQuantity(book.getStockQuantity());

            return bookRepository.save(existingBook);
        }

        return null;
    }

}
