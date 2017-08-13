package ap.dao.daoImpl;

import ap.dao.BasicDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BasicDaoImpl<T> implements BasicDao<T> {
    @Autowired
    SessionFactory sessionFactory;

    public void setType(Class<T> type) {
        this.type = type;
    }

    private Class<T> type;

    public BasicDaoImpl(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public BasicDaoImpl() {
    }

    @Transactional
    public T save(T object) throws HibernateException {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(object);
        return object;
    }

    @Transactional
    public void update(T object) throws HibernateException {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(object);
    }

    @Transactional
    public void delete(T object) throws HibernateException {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(object);
    }

    @Transactional
    public T getByID(int id) throws HibernateException {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(type, id);
    }

    @Transactional
    public List<T> getAll() throws HibernateException {
        Session currentSession = sessionFactory.getCurrentSession();
        Criteria criteria = currentSession.createCriteria(type);
        List<T> list = criteria.list();
        return list;
    }

    @Override
    @Transactional
    public long getCountRow() throws HibernateException  {
        Session currentSession = sessionFactory.getCurrentSession();
        Object result = currentSession.createCriteria(type)
                .setProjection(Projections.rowCount()).uniqueResult();

       return  Long.parseLong(result.toString());
    }
}
