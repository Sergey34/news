package net.news;

import net.news.dao.HeadingDao;
import net.news.dao.NewsDao;
import net.news.dao.RoleDao;
import net.news.dao.UserDao;
import net.news.domain.news.Heading;
import net.news.domain.news.News;
import net.news.domain.users.Role;
import net.news.domain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class TmpConfig {
    public static final Map<String, Role> roles;

    static {
        roles = new HashMap<>();
        roles.put("ADMIN", Role.builder().roleName("ADMIN").build());
        roles.put("USER", Role.builder().roleName("USER").build());
    }

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final NewsDao dao;
    private final HeadingDao headingDao;

    @Autowired
    public TmpConfig(UserDao userDao, RoleDao roleDao, NewsDao dao, HeadingDao headingDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.dao = dao;
        this.headingDao = headingDao;
    }


    @PostConstruct
    public void init() {
        initUsers();
        initNews();

    }

    private void initUsers() {
        roleDao.save(roles.values());
        User sergey = User.builder().name("sergey")
                .email("sergey@mail.com")
                .login("seko")
                .password(new BCryptPasswordEncoder().encode("pass"))
                .roles(new HashSet<>(roles.values())).build();
        userDao.save(sergey);
        User admin = User.builder().name("admin")
                .email("admin@mail.com")
                .login("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .roles(new HashSet<>(roles.values())).build();
        userDao.save(admin);
        User user = User.builder().name("user")
                .email("user@mail.com")
                .login("user")
                .password(new BCryptPasswordEncoder().encode("user"))
                .roles(new HashSet<>(Arrays.asList(roles.get("USER")))).build();
        userDao.save(user);

        Iterable<User> all = userDao.findAll();
        System.out.println(all);
    }

    private void initNews() {
        Heading heading = Heading.builder().name("blaРубрика").build();
        Heading heading2 = Heading.builder().name("blaРубрика2").build();
        List<Heading> headings = Arrays.asList(heading, heading2);
        headingDao.save(headings);
        String anons = "Группа «Самое Большое Простое Число» выпустила новый мини-альбом — «Выброшу голову — пусть думает сердце!».\n" +
                "\n" +
                "Наш паблик стал первым местом в России, где его можно послушать бесплатно и легально. \n" +
                "\n" +
                "Полная версия мини-альбома доступна при клике на картинку. Также его можно приобрести в iTunes";
        String text = "blaTi tleblaTitle blaTitlebl aTitleblaTitl eblaTitlebla TitleblaTitle blaTitleb laTitlebl aTitleblaT itleblaTitl eblaTitl eblaTitle blaTitleblaTi tleblaTitlebl aTitleblaTitleblaTitleblaTitle blaTitleblaTitleblaTitleblaT itleblaTitleblaTitleblaTitl eblaTitleblaTitleblaTitleb laTitleblaTitleblaTitle blaTitleblaTitleblaTi tleblaTitleblaTitleblaTit leblaTitleblaTitleblaTitleblaTitle blaTitleblaTitleblaTitleblaTitle" +
                "blaTitlebla TitleblaTitleblaTitl eblaTitleblaTitlebla TitleblaTitleblaTitle blaTitleblaTitleblaTitleblaTi tleblaTitleblaTitleblaTi tleblaTitleblaTitleblaTitle blaTitleblaTitleblaTitleblaTitleblaTitleblaTitleblaTitle blaTitleblaTitleblaTi tleblaTitleblaTitleb laTitleblaTitlebla TitleblaTitleblaTitleb laTitleblaTitleblaTitl eblaTitleblaTitleblaTit lebla";
        News news = News.builder()
                .anons(anons)
                .autor(userDao.findOneByLogin("user"))
                .date(new Date())
                .title("TitleBla")
                .text(text)
                .heading(headings).build();
        dao.save(news);
        News news2 = News.builder()
                .anons("bla2")
                .autor(userDao.findOneByLogin("seko"))
                .date(new Date())
                .title("blaTitle2")
                .text("blablablabla2")
                .heading(headings).build();
        dao.save(news2);
    }

}
