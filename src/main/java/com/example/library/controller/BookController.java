package com.example.library.controller;

import com.example.library.entity.Books;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books", produces = "application/json")
public class BookController {

    @Autowired
    private BookService bookService;

    // GET ALL BOOKS
    @GetMapping("/getAllBooks")
    public ResponseEntity<Object> getAllBooks() {
        return bookService.getAllBooks();
    }

    // ADD BOOK
    @PostMapping("/addBook")
    public ResponseEntity<Object> addBook(@Valid @RequestBody Books book) {
        return bookService.addBook(book);
    }

    // GET BOOK BY NAME
    @GetMapping("/getBookByName")
    public ResponseEntity<Object> getBookByName(@RequestParam String title) {
        return bookService.getBookByName(title);
    }

    // DELETE BOOK
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Integer id) {
        return bookService.deleteBook(id);
    }

    // UPDATE BOOK
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Object> updateBook(
            @PathVariable Integer id,
            @RequestBody Books book) {
        return bookService.updateBook(id, book);
    }
}
