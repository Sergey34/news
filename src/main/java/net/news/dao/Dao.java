package net.news.dao;

import lombok.NonNull;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Repository
@Transactional
public class Dao {
    @Autowired
    private SessionFactory sessionFactory;

    public <T> List<T> load(String sql, @NonNull T entity, Map<String, Object> parameters) {
        SQLQuery sqlQuery = getSqlQuery(sql);
        sqlQuery.addEntity(entity.getClass());
        parameters.forEach(sqlQuery::setParameter);
        //noinspection unchecked
        return (List<T>) sqlQuery.list();
    }

    public <T> List<T> load(String sql, T entity) {
        return load(sql, entity, Collections.emptyMap());
    }

    public <T> List<T> load(String sql, Map<String, Object> parameters, Function<List, List<T>> mapper) {
        SQLQuery sqlQuery = getSqlQuery(sql);
        parameters.forEach(sqlQuery::setParameter);
        return mapper.apply(sqlQuery.list());
    }

    private SQLQuery getSqlQuery(String sql) {
        return getCurrentSession().createSQLQuery(sql);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
