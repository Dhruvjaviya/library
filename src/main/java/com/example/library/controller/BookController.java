package com.example.library.controller;

import com.example.library.entity.Books;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // GET ALL BOOKS
    @GetMapping("/getAllBooks")
    public List<Books> getAllBooks() {
        List<Books> booksList = bookService.getAllBooks();
        System.out.println(booksList);
        return booksList;
    }

    // ADD BOOK
    @PostMapping("/addBook")
    public Books addBook(@RequestBody Books book) {
        Books savedBook = bookService.addBook(book);
        System.out.println(savedBook);
        return savedBook;
    }

    // GET BOOK BY NAME
    @GetMapping("/getBookByName")
    public Books getBookByName(@RequestParam String title) {
        Books book = bookService.getBookByName(title);
        System.out.println(book);
        return book;
    }

    // DELETE BOOK
    @DeleteMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        System.out.println("Deleted Book ID: " + id);
        return "Book deleted successfully";
    }

    // UPDATE BOOK
    @PutMapping("/updateBook/{id}")
    public Books updateBook(@PathVariable Integer id, @RequestBody Books book) {
        Books updatedBook = bookService.updateBook(id, book);
        System.out.println(updatedBook);
        return updatedBook;
    }

}
