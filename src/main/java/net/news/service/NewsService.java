package net.news.service;

import lombok.extern.slf4j.Slf4j;
import net.news.dao.DaoHeading;
import net.news.dao.DaoNews;
import net.news.domain.news.Heading;
import net.news.domain.news.News;
import net.news.dto.Menu;
import net.news.dto.NewsDto;
import net.news.service.converters.ConverterNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class NewsService {
    private final DaoNews dao;
    private final DaoHeading daoHeading;
    private final ConverterNews converterNews;
    private final ApplicationContext context;
    private final UserService userService;
    @Value("${cursor.size}")
    private int size;

    @Autowired
    public NewsService(DaoNews dao, DaoHeading daoHeading, ConverterNews converterNews, ApplicationContext context, UserService userService) {
        this.dao = dao;
        this.daoHeading = daoHeading;
        this.converterNews = converterNews;
        this.context = context;
        this.userService = userService;
    }

    public NewsDto findById(long id) {
        News news = dao.findOne(id);
        return converterNews.newsToNewsDto(news);
    }

    public List<Menu> findHeadings() {
        return converterNews.headingsToMenu(daoHeading.findAll());
    }

    public List<NewsDto> findByHeadingName(String name, int page) {
        return converterNews.newsToNewsDto(dao.findByHeading_name(name, new PageRequest(page, size)));
    }

    public List<NewsDto> findByAuthor(String login, int page) {
        return converterNews.newsToNewsDto(dao.findByAuthor_login(login, new PageRequest(page, size)));
    }

    public List<NewsDto> findByDate(String dateStr, int page) {
        DateManager dateManager;
        try {
            dateManager = ((DateManager) context.getBean("dateManager")).calculate(dateStr);
        } catch (ParseException e) {
            log.warn("Некоректная дата", e);
            return new ArrayList<>();
        }
        Date startDate = dateManager.getStartDate();
        Date stopDate = dateManager.getStopDate();

        List<News> newsList = dao.findByDateBetween(startDate, stopDate, new PageRequest(page, size));
        return converterNews.newsToNewsDto(newsList);
    }

    @PostConstruct
    public void init() throws ParseException {
    }

    public int getCountPageFindByDate(String dateStr) {
        DateManager dateManager;
        try {
            dateManager = ((DateManager) context.getBean("dateManager")).calculate(dateStr);
        } catch (ParseException e) {
            log.warn("Некоректная дата", e);
            return 0;
        }
        Date startDate = dateManager.getStartDate();
        Date stopDate = dateManager.getStopDate();
        return calculateCountPages(dao.countByDateBetween(startDate, stopDate));
    }

    private int calculateCountPages(int count) {
        return (int) Math.ceil(count * 1.0 / size);
    }

    public int getCountPageFindByAuthor(String login) {
        return calculateCountPages(dao.countByAuthor_login(login));
    }

    public int getCountPageFindByHeadingName(String heading) {
        return calculateCountPages(dao.countByHeading_name(heading));
    }

    public boolean saveNews(String title, String anons, String text, List<String> headingNameList) {
        if (headingNameList.size() == 0) {
            return false;
        }
        News news = News.builder()
                .title(title)
                .anons(anons)
                .text(text)
                .heading(gitHeadingsByName(headingNameList))
                .date(new Date())
                .author(userService.getCurrentUser()).build();
        dao.save(news);
        return true;
    }


    private List<Heading> gitHeadingsByName(List<String> headingNameList) {
        List<Heading> headings = new ArrayList<>(headingNameList.size());
        for (String headingName : headingNameList) {
            Heading heading = daoHeading.findOneByName(headingName);
            if (heading != null) {
                headings.add(heading);
            }
        }
        return headings;
    }

    public List<NewsDto> findAll(int page) {
        return converterNews.newsToNewsDto(dao.findAll(new PageRequest(page, size)).getContent());
    }

    public long count() {
        return dao.count();
    }
}
