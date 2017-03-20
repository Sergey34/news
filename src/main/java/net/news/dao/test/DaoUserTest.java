package net.news.dao.test;

import net.news.domain.users.test.UserNew;
import org.springframework.data.repository.CrudRepository;

public interface DaoUserTest extends CrudRepository<UserNew, Integer> {
}
