package net.news.dao;

import net.news.domain.news.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface DaoNews extends CrudRepository<News, Long>, PagingAndSortingRepository<News, Long>, JpaSpecificationExecutor<News> {

    List<News> findByHeading_name(String name, Pageable pageable);

    List<News> findByAuthor_login(String login, Pageable pageable);

    List<News> findByDateBetween(Date dateStart, Date dateStop, Pageable pageable);

    int countByAuthor_login(String login);

    int countByDateBetween(Date startDate, Date stopDate);

    int countByHeading_name(String heading);

    @Override
    Page<News> findAll(Pageable pageable);

    News findOneById(long id);
}
