package com.example.library.dao.impl;

import com.example.library.dao.BookDao;
import com.example.library.entity.Books;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.*;
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
    @Override
    public Page<Books> findAll(Pageable pageable) {

        String jpql = "SELECT b FROM Books b";
        TypedQuery<Books> query = entityManager.createQuery(jpql, Books.class);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Books> books = query.getResultList();

        Long total = entityManager
                .createQuery("SELECT COUNT(b) FROM Books b", Long.class)
                .getSingleResult();

        return new PageImpl<>(books, pageable, total);
    }

}
