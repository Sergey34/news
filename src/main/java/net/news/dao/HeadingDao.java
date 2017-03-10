package net.news.dao;

import net.news.domain.Heading;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HeadingDao extends CrudRepository<Heading, Long> {
    @Override
    List<Heading> findAll();
}
