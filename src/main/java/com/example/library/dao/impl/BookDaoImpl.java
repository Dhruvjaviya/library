package com.example.library.dao.impl;

import com.example.library.dao.BookDao;
import com.example.library.entity.Books;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Books> findAll() {
        return entityManager.createQuery("FROM Books", Books.class).getResultList();
    }

    @Override
    public Books findById(Integer id) {
        return entityManager.find(Books.class, id);
    }

    @Override
    public Books findByTitle(String title) {
        return entityManager.createQuery(
                        "FROM Books b WHERE b.title = :title", Books.class)
                .setParameter("title", title)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Books save(Books book) {
        if (book.getBookId() == null) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(Integer id) {
        Books book = entityManager.find(Books.class, id);
        if (book != null) {
            entityManager.remove(book);
        }
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(b) FROM Books b WHERE b.isbn = :isbn", Long.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
        return count > 0;
    }
}
