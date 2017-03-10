package net.news.dao;

import net.news.domain.news.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NewsDao extends CrudRepository<News, Long> {

    List<News> findByHeading_name(String name);

    List<News> findByAuthor_login(String login);
}
