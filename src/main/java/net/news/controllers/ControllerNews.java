package net.news.controllers;

import net.news.domain.users.Role;
import net.news.dto.Menu;
import net.news.dto.NewsDto;
import net.news.dto.UserDto;
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(Map<String, Object> model) {
        return "redirect:/lenta";
    }

    @RequestMapping(value = {"/lenta/{page}", "/lenta"}, method = RequestMethod.GET)
    public String getAllNews(@PathVariable(value = "page", required = false) Integer page, Map<String, Object> model) {
        page = page == null ? 1 : page;
        Iterable<Menu> headings = service.findHeadings();
        List<NewsDto> news = service.findAll(page - 1);
        long countPage = service.count();
        model.put("menu", headings);
        model.put("newsList", news);
        model.put("raquo", page + 1);
        model.put("laquo", page - 1);
        model.put("count", countPage);
        model.put("url", "/lenta");
        model.put("root", "root");
        model.put("currentPage", page);
        model.put("login", userService.getLoginCurrentUser());
        return "newsList";
    }

    @RequestMapping(value = "/addNews", method = RequestMethod.GET)
    public String getNews(Map<String, Object> model) {
        Iterable<Menu> headings = service.findHeadings();
        model.put("menu", headings);
        model.put("login", userService.getLoginCurrentUser());
        return "addNews";

    }

    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
    public String addNews(@RequestParam("title") String title,
                          @RequestParam("anons") String anons,
                          @RequestParam("text") String text,
                          @RequestParam(value = "headings", required = false) List<String> headingNameList,
                          Map<String, Object> model) {
        boolean saved = service.saveNews(title, anons, text, headingNameList);
        if (!saved) {
            model.put("error", "Новость не добавлена");
        } else {
            model.put("completed", "Успешно");
        }
        Iterable<Menu> headings = service.findHeadings();
        model.put("menu", headings);
        model.put("login", userService.getLoginCurrentUser());
        return "addNews";
    }

    @RequestMapping("/login")
    public String login(Map<String, Object> model) {
        Iterable<Menu> headings = service.findHeadings();
        model.put("menu", headings);
        model.put("login", userService.getLoginCurrentUser());
        return "login";
    }

    @RequestMapping("/news/{id}")
    public String getNews(@PathVariable("id") long id, Map<String, Object> model) {
        NewsDto news = service.findById(id);
        Iterable<Menu> headings = service.findHeadings();
        model.put("news", news);
        model.put("isAdmin", userService.currentUserIsAdmin() || service.userIsAuthor(id));
        model.put("menu", headings);
        model.put("login", userService.getLoginCurrentUser());
        return "news";
    }

    @RequestMapping({"/heading/{heading}/{page}", "/heading/{heading}"})
    public String getNewsByHeading(@PathVariable(value = "heading") String heading,
                                   @PathVariable(value = "page", required = false) Integer page,
                                   Map<String, Object> model) {
        page = page == null ? 1 : page;
        List<NewsDto> news = service.findByHeadingName(heading, page - 1);
        Iterable<Menu> headings = service.findHeadings();
        int countPage = service.getCountPageFindByHeadingName(heading);
        model.put("newsList", news);
        model.put("menu", headings);
        model.put("heading", heading);
        model.put("raquo", page + 1);
        model.put("laquo", page - 1);
        model.put("count", countPage);
        model.put("url", "/heading/" + heading);
        model.put("currentPage", page);
        model.put("login", userService.getLoginCurrentUser());
        return "newsList";
    }

    @RequestMapping({"/author/{login}/{page}", "/author/{login}"})
    public String getNewsByAuthor(@PathVariable("login") String login,
                                  @PathVariable(value = "page", required = false) Integer page,
                                  Map<String, Object> model) {
        page = page == null ? 1 : page;
        List<NewsDto> news = service.findByAuthor(login, page - 1);
        Iterable<Menu> headings = service.findHeadings();
        int countPage = service.getCountPageFindByAuthor(login);
        model.put("newsList", news);
        model.put("menu", headings);
        model.put("user", login);
        model.put("raquo", page + 1);
        model.put("laquo", page - 1);
        model.put("count", countPage);
        model.put("url", "/author/" + login);
        model.put("currentPage", page);
        model.put("login", userService.getLoginCurrentUser());
        return "newsList";
    }

    @RequestMapping(value = {"/date/{date}", "/date/{date}/{page}"}, method = RequestMethod.GET)
    public String getNewsByDate(@PathVariable("date") String dateStr,
                                @PathVariable(value = "page", required = false) Integer page,
                                Map<String, Object> model) {
        page = page == null ? 1 : page;
        List<NewsDto> news = service.findByDate(dateStr, page - 1);
        Iterable<Menu> headings = service.findHeadings();
        int countPage = service.getCountPageFindByDate(dateStr);
        model.put("newsList", news);
        model.put("menu", headings);
        model.put("date", dateStr);
        model.put("raquo", page + 1);
        model.put("laquo", page - 1);
        model.put("count", countPage);
        model.put("url", "/date/" + dateStr);
        model.put("currentPage", page);
        model.put("login", userService.getLoginCurrentUser());
        return "newsList";
    }

    @RequestMapping(value = {"/adminka", "/adminka/{column}/{sort}"}, method = RequestMethod.GET)
    public String getNewsByDate(@PathVariable(value = "column", required = false) String column,
                                @PathVariable(value = "sort", required = false) String sort,
                                Map<String, Object> model) {
        Iterable<Menu> headings = service.findHeadings();
        List<UserDto> users = userService.gitAllUsers(column, sort);
        List<Role> roles = userService.getAllRoles();
        model.put("menu", headings);
        model.put("roles", roles);
        model.put("users", users);
        model.put("sort", "asc".equals(sort) ? "desc" : "asc");
        model.put("login", userService.getLoginCurrentUser());
        return "adminka";
    }

    @RequestMapping(value = {"/addUser"}, method = RequestMethod.POST)
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("login") String login,
                          @RequestParam("email") String email,
                          @RequestParam("password") String pass,
                          @RequestParam(value = "roles", required = false) List<String> rolesName,
                          Map<String, Object> model) {
        boolean saved = userService.addUser(login, name, pass, rolesName, email);
        if (!saved) {
            model.put("error", "Пользователь с таким логином уже существует.");
        } else {
            model.put("completed", "Успешно");
        }
        Iterable<Menu> headings = service.findHeadings();
        List<UserDto> users = userService.gitAllUsers(null, null);
        model.put("menu", headings);
        model.put("users", users);
        model.put("sort", "asc");
        model.put("login", userService.getLoginCurrentUser());
        return "adminka";
    }

    @RequestMapping(value = {"/updateUser"}, method = RequestMethod.POST)
    public String updateUser(@RequestParam("name") String name,
                             @RequestParam("login") String login,
                             @RequestParam("email") String email,
                             @RequestParam("password") String pass,
                             @RequestParam("passwordOld") String passOld,
                             @RequestParam(value = "roles", required = false) List<String> rolesName,
                             Map<String, Object> model) {
        boolean saved = userService.update(login, name, pass, rolesName, email, passOld);
        if (!saved) {
            model.put("error", "Неверный пароль или логин уже занят");
        } else {
            model.put("completed", "Успешно");
        }
        Iterable<Menu> headings = service.findHeadings();
        UserDto userDto = userService.getUser(login);
        List<Role> roles = userService.getAllRoles();
        model.put("menu", headings);
        model.put("roles", roles);
        model.put("user", userDto);
        model.put("login", userService.getLoginCurrentUser());
        return "user";
    }


    @RequestMapping(value = {"/user/{login}", "/user"}, method = RequestMethod.GET)
    public String user(@PathVariable(value = "login", required = false) String login,
                       Map<String, Object> model) {
        Iterable<Menu> headings = service.findHeadings();
        UserDto userDto = userService.getUser(login);
        List<Role> roles = userService.getAllRoles();
        model.put("menu", headings);
        model.put("roles", roles);
        model.put("user", userDto);
        model.put("login", userService.getLoginCurrentUser());
        return "user";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteNews(@PathVariable("id") long id, Map<String, Object> model) {
        service.delete(id);
        return "redirect:/lenta";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editNews(@PathVariable("id") long id, Map<String, Object> model) {
        NewsDto news = service.findById(id);
        Iterable<Menu> headings = service.findHeadings();
        model.put("menu", headings);
        model.put("login", userService.getLoginCurrentUser());
        model.put("news", news);
        return "editNews";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateNews(@PathVariable("id") long id,
                             @RequestParam("title") String title,
                             @RequestParam("anons") String anons,
                             @RequestParam("text") String text,
                             @RequestParam(value = "headings", required = false) List<String> headingNameList,
                             Map<String, Object> model) {
        boolean saved = service.update(title, anons, text, headingNameList, id);
        if (!saved) {
            model.put("error", "Новость не обновлена");
        } else {
            model.put("completed", "Успешно");
        }
        Iterable<Menu> headings = service.findHeadings();
        model.put("menu", headings);
        model.put("login", userService.getLoginCurrentUser());
        return "redirect:/";
    }

}
