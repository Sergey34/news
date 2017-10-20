package net.news;

import net.news.dao.*;
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
        roles.put("ADMIN", Role.builder().authority("ROLE_ADMIN").build());
        roles.put("USER", Role.builder().authority("ROLE_USER").build());
    }

    private final DaoUser daoUser;
    private final DaoRole daoRole;
    private final DaoNews dao;
    private final DaoHeading daoHeading;
    @Autowired
    Test test;


    @Autowired
    public TmpConfig(DaoUser daoUser, DaoRole daoRole, DaoNews dao, DaoHeading daoHeading) {
        this.daoUser = daoUser;
        this.daoRole = daoRole;
        this.dao = dao;
        this.daoHeading = daoHeading;
    }

    @Autowired
    Dao daoNativ;

    @PostConstruct
    public void init() {
        initUsers();
        initNews();
        test.findBySpecification();
        List<User> load = daoNativ.load("select * from User", new User());
        System.out.println(load);
    }

    private void initUsers() {
        daoRole.save(roles.values());
        User sergey = User.builder().name("sergey")
                .email("sergey@mail.com")
                .login("seko")
                .password(new BCryptPasswordEncoder().encode("pass"))
                .roles(new HashSet<>(roles.values())).build();
        daoUser.save(sergey);
        User admin = User.builder().name("admin")
                .email("admin@mail.com")
                .login("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .roles(new HashSet<>(roles.values())).build();
        daoUser.save(admin);
        User user = User.builder().name("user")
                .email("user@mail.com")
                .login("user")
                .password(new BCryptPasswordEncoder().encode("user"))
                .roles(new HashSet<>(Arrays.asList(roles.get("USER")))).build();
        daoUser.save(user);

        Iterable<User> all = daoUser.findAll();
        System.out.println(all);
    }

    private void initNews() {
        Heading heading = Heading.builder().name("blaРубрика").build();
        Heading heading2 = Heading.builder().name("blaРубрика2").build();
        List<Heading> headings = Arrays.asList(heading, heading2);
        daoHeading.save(headings);
        String anons = "Группа «Самое Большое Простое Число» выпустила новый мини-альбом — «Выброшу голову — пусть думает сердце!».\n" +
                "\n" +
                "Наш паблик стал первым местом в России, где его можно послушать бесплатно и легально. \n" +
                "\n" +
                "Полная версия мини-альбома доступна при клике на картинку. Также его можно приобрести в iTunes";
        String text = "blaTi tleblaTitle blaTitlebl aTitleblaTitl eblaTitlebla TitleblaTitle blaTitleb laTitlebl aTitleblaT itleblaTitl eblaTitl eblaTitle blaTitleblaTi tleblaTitlebl aTitleblaTitleblaTitleblaTitle blaTitleblaTitleblaTitleblaT itleblaTitleblaTitleblaTitl eblaTitleblaTitleblaTitleb laTitleblaTitleblaTitle blaTitleblaTitleblaTi tleblaTitleblaTitleblaTit leblaTitleblaTitleblaTitleblaTitle blaTitleblaTitleblaTitleblaTitle" +
                "blaTitlebla TitleblaTitleblaTitl eblaTitleblaTitlebla TitleblaTitleblaTitle blaTitleblaTitleblaTitleblaTi tleblaTitleblaTitleblaTi tleblaTitleblaTitleblaTitle blaTitleblaTitleblaTitleblaTitleblaTitleblaTitleblaTitle blaTitleblaTitleblaTi tleblaTitleblaTitleb laTitleblaTitlebla TitleblaTitleblaTitleb laTitleblaTitleblaTitl eblaTitleblaTitleblaTit lebla";
        News news = News.builder()
                .anons(anons)
                .author(daoUser.findOneByLogin("user"))
                .date(new Date())
                .title("TitleBla")
                .text(text)
                .heading(headings).build();
        dao.save(news);
        News news2 = News.builder()
                .anons("bla2")
                .author(daoUser.findOneByLogin("seko"))
                .date(new Date())
                .title("blaTitle2")
                .text("blablablabla2")
                .heading(headings).build();
        dao.save(news2);
    }

}
