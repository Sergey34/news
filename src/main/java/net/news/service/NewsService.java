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
    private final ConverterNews converterNews;


    @Autowired
    public NewsService(NewsDao dao, HeadingDao headingDao, ConverterNews converterNews) {
        this.dao = dao;
        this.headingDao = headingDao;
        this.converterNews = converterNews;
    }

    public NewsDto findById(long id) {
        News news = dao.findOne(id);
        return converterNews.newsToNewsDto(news);
    }

    public List<Menu> findHeadings() {
        return converterNews.headingsToMenu(headingDao.findAll());
    }

    public List<NewsDto> findByHeadingName(String name) {
        return converterNews.newsToNewsDto(dao.findByHeading_name(name));
    }

    public List<NewsDto> findByAuthor(String login) {
        return converterNews.newsToNewsDto(dao.findByAuthor_login(login));
    }
}
