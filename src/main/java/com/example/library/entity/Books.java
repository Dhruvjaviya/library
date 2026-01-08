package com.example.library.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "books")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Book entity representing a book in the library")
public class Books {

    @Schema(description = "Unique identifier for the book", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Schema(description = "Title of the book", example = "The Great Gatsby", required = true)
    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;

    @Schema(description = "Author of the book", example = "F. Scott Fitzgerald", required = true)
    @NotBlank(message = "Author is required")
    @Column(name = "author", nullable = false)
    private String author;

    @Schema(description = "Genre of the book", example = "Fiction")
    @Column(name = "genre")
    private String genre;

    @Schema(description = "ISBN number of the book", example = "978-0-7432-7356-5", required = true)
    @NotBlank(message = "ISBN is required")
    @Column(name = "isbn", unique = true)
    private String isbn;

    @Schema(description = "Publisher of the book", example = "Scribner")
    @Column(name = "publisher")
    private String publisher;

    @Schema(description = "Year of publication", example = "1925")
    @Min(value = 1000, message = "Publication year must be valid")
    @Column(name = "publication_year")
    private Integer publicationYear;

    @Schema(description = "Price of the book", example = "12.99", required = true)
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    private Double price;

    @Schema(description = "Stock quantity available", example = "50", required = true)
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Schema(description = "Timestamp when the book was added to the system", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ===================== GETTERS & SETTERS =====================

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
