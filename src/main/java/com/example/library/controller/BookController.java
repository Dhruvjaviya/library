package com.example.library.controller;

import com.example.library.entity.Books;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books", produces = "application/json")
@Tag(name = "Books", description = "Book management APIs for CRUD operations and pagination")
@SecurityRequirement(name = "Bearer Authentication")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(
            summary = "Get all books",
            description = "Retrieve a list of all books in the library"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping("/getAllBooks")
    public ResponseEntity<Object> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Operation(
            summary = "Add a new book",
            description = "Add a new book to the library. Required fields: title, author, isbn, price, stockQuantity"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping("/addBook")
    public ResponseEntity<Object> addBook(@Valid @RequestBody Books book) {
        return bookService.addBook(book);
    }

    @Operation(
            summary = "Get book by title",
            description = "Search for a book by its title"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping("/getBookByName")
    public ResponseEntity<Object> getBookByName(
            @Parameter(description = "Book title to search for", required = true)
            @RequestParam String title) {
        return bookService.getBookByName(title);
    }

    @Operation(
            summary = "Delete a book",
            description = "Delete a book from the library by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted successfully",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Object> deleteBook(
            @Parameter(description = "Book ID to delete", required = true)
            @PathVariable Integer id) {
        return bookService.deleteBook(id);
    }

    @Operation(
            summary = "Update a book",
            description = "Update an existing book's information by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Object> updateBook(
            @Parameter(description = "Book ID to update", required = true)
            @PathVariable Integer id,
            @RequestBody Books book) {
        return bookService.updateBook(id, book);
    }
    @Operation(
            summary = "Get books with pagination",
            description = "Retrieve books with pagination, sorting, and filtering support"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping("/page")
    public ResponseEntity<?> getBooksWithPagination(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "5")
            @RequestParam(defaultValue = "5") int size,
            @Parameter(description = "Field to sort by", example = "bookId")
            @RequestParam(defaultValue = "bookId") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(
                bookService.getBooksWithPagination(page, size, sortBy, sortDir)
        );
    }
}
