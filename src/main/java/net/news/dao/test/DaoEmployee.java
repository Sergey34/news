package net.news.dao.test;

import net.news.domain.users.test.Employee;
import org.springframework.data.repository.CrudRepository;

public interface DaoEmployee extends CrudRepository<Employee, Integer> {
}
