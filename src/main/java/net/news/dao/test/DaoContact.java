package net.news.dao.test;

import net.news.domain.users.test.Contact;
import org.springframework.data.repository.CrudRepository;


public interface DaoContact extends CrudRepository<Contact, Integer> {
}
