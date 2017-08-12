package ap.dao;

import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.List;

public interface BasicDao<T> {
    T save(T object) throws HibernateException;

    void update(T object) throws HibernateException;

    void delete(T object) throws HibernateException;

    T getByID(int id) throws HibernateException;

    List<T> getAll() throws HibernateException;
}
