package com.distribuida.servicios;

import com.distribuida.db.Books;
import io.helidon.common.reactive.Single;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class BooksRepositoryImpl implements BooksRepository {
    @Inject
    private EntityManager entityManager;


    @Override
    public List findAll() {
     return entityManager.createQuery("select b from Books b").getResultList();
    }

    @Override
    public Optional<Books> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Books.class, id));
    }

    @Override
    public void insert(Books book) {
        entityManager.persist(book);
    }

    @Override
    public void update(Books book) {
        entityManager.merge(book);
    }

    @Override
    public void delete(Integer id) {
        if (findById(id).isPresent()) {
            entityManager.remove(findById(id));
        }
    }


}
