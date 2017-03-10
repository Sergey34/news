package net.news.controllers;

import net.news.dto.Menu;
import net.news.dto.NewsDto;
import net.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ControllerNews {
    @Autowired
    NewsService service;

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
        return "newsList";
    }
}
