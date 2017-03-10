package net.news.dao;

import net.news.domain.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findOneByLogin(String login);
}
