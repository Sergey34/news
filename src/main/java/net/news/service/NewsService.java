package net.news.service;

import net.news.dao.HeadingDao;
import net.news.dao.NewsDao;
import net.news.domain.news.News;
import net.news.dto.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsDao dao;
    private final HeadingDao headingDao;
    private final Converter converter;


    @Autowired
    public NewsService(NewsDao dao, HeadingDao headingDao, Converter converter) {
        this.dao = dao;
        this.headingDao = headingDao;
        this.converter = converter;
    }

    public News findById(long id) {
        return dao.findOne(id);
    }

    public List<Menu> findHeadings() {
        return converter.headingsToMenu(headingDao.findAll());
    }

    public List<News> findByHeadingName(String name) {
        return dao.findByHeading_name(name);
    }
}
