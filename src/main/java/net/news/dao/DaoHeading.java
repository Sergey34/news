package net.news.dao;

import net.news.domain.news.Heading;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DaoHeading extends CrudRepository<Heading, Long> {
    @Override
    List<Heading> findAll();

    Heading findOneByName(String headingName);
}
