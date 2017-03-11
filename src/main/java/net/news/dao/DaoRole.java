package net.news.dao;

import net.news.domain.users.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface DaoRole extends CrudRepository<Role, Integer> {
    @Override
    List<Role> findAll();
}
