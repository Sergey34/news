package net.news.dao;

import net.news.domain.users.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface DaoUser extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    User findOneByLogin(String login);

    @Override
    List<User> findAll();

    @Override
    List<User> findAll(Sort sort);
}
