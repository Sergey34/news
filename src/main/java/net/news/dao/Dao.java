package net.news.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class Dao {
    @Autowired
    private SessionFactory sessionFactory;

    public <T> List<T> load(String sql, T entity, Map<String, Object> parameters) {
        SQLQuery sqlQuery = getSqlQuery(sql);
        sqlQuery.addEntity(entity.getClass());
        parameters.forEach(sqlQuery::setParameter);
        //noinspection unchecked
        return (List<T>) sqlQuery.list();
    }

    private SQLQuery getSqlQuery(String sql) {
        return getCurrentSession().createSQLQuery(sql);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
