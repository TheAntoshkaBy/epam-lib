package com.epam.esm.repository.jpa;

import com.epam.esm.repository.ShopRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public abstract class ShopJPARepository<T> implements ShopRepository<T> { //fixme имплементить интерфейс + иерархия интерфейсов добавить
    @PersistenceContext
    protected EntityManager entityManager;

    public T create(T t){
        entityManager.persist(t);
        return t;
    }
}