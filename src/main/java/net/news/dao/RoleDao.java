package net.news.dao;

import net.news.domain.users.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface RoleDao extends CrudRepository<Role, Integer> {
}
