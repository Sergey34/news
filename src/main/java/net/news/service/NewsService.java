package net.news.service;

import net.news.dao.HeadingDao;
import net.news.dao.NewsDao;
import net.news.domain.Heading;
import net.news.domain.News;
import net.news.dto.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {
    private final NewsDao dao;
    private final HeadingDao headingDao;

    @Autowired
    public NewsService(NewsDao dao, HeadingDao headingDao, Converter converter) {
        this.dao = dao;
        this.headingDao = headingDao;
        this.converter = converter;
    }

    @PostConstruct
    public void init() {
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
                .autor("blabor")
                .date(new Date())
                .title("TitleBla")
                .text(text)
                .heading(headings).build();
        dao.save(news);
        News news2 = News.builder()
                .anons("bla2")
                .autor("blabor2")
                .date(new Date())
                .title("blaTitle2")
                .text("blablablabla2")
                .heading(headings).build();
        dao.save(news2);
    }

    public News findById(long id) {
        return dao.findOne(id);
    }

    private final Converter converter;

    public List<Menu> findHeadings() {
        return converter.headingsToMenu(headingDao.findAll());
    }

    public List<News> findByHeadingName(String name) {
        return dao.findByHeading_name(name);
    }
}
