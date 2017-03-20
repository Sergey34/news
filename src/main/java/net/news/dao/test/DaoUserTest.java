package net.news.dao.test;

import net.news.domain.users.test.UserNew;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DaoUserTest extends CrudRepository<UserNew, Integer> {
    @Query("from UserNew where login = ?1")
    UserNew findOneByLogin(String login);
}
