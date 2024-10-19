package org.example.Repositories;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T,K extends Serializable> implements CRUD<T,K>{
    protected Class<T> clazz;
    @Getter
    protected Session sessionProxy;

    public BaseRepository(Session sessionProxy, Class clazz){
        this.sessionProxy = sessionProxy;
        this.clazz=clazz;
    }

    @Override
    public T save(T entity) {
        sessionProxy.save(entity);
        return entity;
    }

    @Override
    public List<T> getAll() {
            Query query = sessionProxy.createQuery("FROM "+ clazz.getName(), clazz);
            return query.getResultList();
    }

    @Override
    public Optional<T> getById(K id) {
        return Optional.ofNullable(sessionProxy.get(clazz, id));
    }

    @Override
    public T update(T entity) {
        sessionProxy.merge(entity);
        sessionProxy.flush();
        return entity;
    }

    @Override
    public void delete(K id) {
        sessionProxy.delete(sessionProxy.get(clazz, id));
        sessionProxy.flush();
    }
}
