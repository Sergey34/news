package net.news.service;

import net.news.dao.HeadingDao;
import net.news.dao.NewsDao;
import net.news.domain.news.News;
import net.news.dto.Menu;
import net.news.dto.NewsDto;
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

    public NewsDto findById(long id) {
        News news = dao.findOne(id);
        return converter.newsToNewsDto(news);
    }

    public List<Menu> findHeadings() {
        return converter.headingsToMenu(headingDao.findAll());
    }

    public List<NewsDto> findByHeadingName(String name) {
        return converter.newsToNewsDto(dao.findByHeading_name(name));
    }
}
