package net.news.controllers;

import net.news.dto.Menu;
import net.news.dto.NewsDto;
import net.news.service.NewsService;
import net.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ControllerNews {
    private final NewsService service;
    private final UserService userService;

    @Autowired
    public ControllerNews(NewsService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @RequestMapping("/news/{id}")
    public String getNews(@PathVariable("id") long id, Map<String, Object> model) {
        NewsDto news = service.findById(id);
        Iterable<Menu> headings = service.findHeadings();
        model.put("news", news);
        model.put("menu", headings);
        return "news";
    }

    @RequestMapping("/{heading}")
    public String getNewsByHeading(@PathVariable("heading") String heading, Map<String, Object> model) {
        List<NewsDto> news = service.findByHeadingName(heading);
        Iterable<Menu> headings = service.findHeadings();
        model.put("newsList", news);
        model.put("menu", headings);
        model.put("heading", heading);
        return "newsList";
    }

    @RequestMapping("/author/{login}")
    public String getNewsByAuthor(@PathVariable("login") String login, Map<String, Object> model) {
        List<NewsDto> news = service.findByAuthor(login);
        Iterable<Menu> headings = service.findHeadings();
        model.put("newsList", news);
        model.put("menu", headings);
        model.put("user", login);
        return "newsList";
    }


    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public String getNewsByDate(@RequestParam("date") String dateStr, Map<String, Object> model) {
        service.findByDate(dateStr);

        return "newsList";

    }
}
