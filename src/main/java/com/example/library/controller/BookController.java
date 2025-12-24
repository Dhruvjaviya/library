package com.example.library.controller;

import com.example.library.entity.Books;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // GET ALL BOOKS
    @GetMapping("/getAllBooks")
    public List<Books> getAllBooks() {
        List<Books> booksList =  bookRepository.findAll();
        System.out.println(booksList);
        return booksList;
    }

    // ADD BOOK
    @PostMapping("/addBook")
    public Books addBook(@RequestBody Books book) {
        Books allBook =bookRepository.save(book);
        System.out.println(allBook);
        return bookRepository.save(book);
    }

    // GET BOOK BY NAME
    @GetMapping("/getBookByName")
    public Books getBookByName(@RequestParam String title) {
        return bookRepository.findByTitle(title);
    }

    // DELETE BOOK
    @DeleteMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookRepository.deleteById(id);
        return "Book deleted successfully";
    }

}
