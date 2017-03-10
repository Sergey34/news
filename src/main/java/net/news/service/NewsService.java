package net.news.service;

import net.news.dao.HeadingDao;
import net.news.dao.NewsDao;
import net.news.domain.news.News;
import net.news.dto.Menu;
import net.news.dto.NewsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@lombok.extern.slf4j.Slf4j
public class NewsService {
    private final NewsDao dao;
    private final HeadingDao headingDao;
    private final ConverterNews converterNews;
    private final ApplicationContext context;


    @Autowired
    public NewsService(NewsDao dao, HeadingDao headingDao, ConverterNews converterNews, ApplicationContext context) {
        this.dao = dao;
        this.headingDao = headingDao;
        this.converterNews = converterNews;
        this.context = context;
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

    public List<NewsDto> findByDate(String dateStr) {
        DateManager dateManager;
        try {
            dateManager = ((DateManager) context.getBean("dateManager")).calculate(dateStr);
        } catch (ParseException e) {
            log.warn("Некоректная дата", e);
            return new ArrayList<>();
        }
        Date startDate = dateManager.getStartDate();
        Date stopDate = dateManager.getStopDate();

        List<News> newsList = dao.findByDateBetween(startDate, stopDate);
        return converterNews.newsToNewsDto(newsList);
    }

    @PostConstruct
    public void init() throws ParseException {

    }

}
