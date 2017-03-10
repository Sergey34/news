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

    @RequestMapping({"/heading/{heading}/{page}", "/heading/{heading}"})
    public String getNewsByHeading(@PathVariable(value = "heading") String heading,
                                   @PathVariable(value = "page", required = false) Integer page,
                                   Map<String, Object> model) {
        List<NewsDto> news = service.findByHeadingName(heading, page == null ? 0 : page);
        Iterable<Menu> headings = service.findHeadings();
        model.put("newsList", news);
        model.put("menu", headings);
        model.put("heading", heading);
        return "newsList";
    }

    @RequestMapping({"/author/{login}/{page}", "/author/{login}"})
    public String getNewsByAuthor(@PathVariable("login") String login,
                                  @PathVariable(value = "page", required = false) Integer page,
                                  Map<String, Object> model) {
        List<NewsDto> news = service.findByAuthor(login, page == null ? 0 : page);
        Iterable<Menu> headings = service.findHeadings();
        model.put("newsList", news);
        model.put("menu", headings);
        model.put("user", login);
        return "newsList";
    }


    @RequestMapping(value = {"/date/{page}", "/date"}, method = RequestMethod.POST)
    public String getNewsByDate(@RequestParam("date") String dateStr,
                                @PathVariable(value = "page", required = false) Integer page,
                                Map<String, Object> model) {
        List<NewsDto> news = service.findByDate(dateStr, page == null ? 0 : page);
        Iterable<Menu> headings = service.findHeadings();
        model.put("newsList", news);
        model.put("menu", headings);
        model.put("date", dateStr);
        return "newsList";

    }
}
