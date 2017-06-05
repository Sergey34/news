package net.news;

import net.news.dao.DaoHelperSpecifications;
import net.news.dao.DaoNews;
import net.news.domain.news.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Test {
    @Autowired
    private DaoNews daoNews;

    public void findBySpecification() {
        List<Pair<String, Object>> attrs = Arrays.asList(Pair.of("name", "blaРубрика"));

        List<News> heading = daoNews.findAll(DaoHelperSpecifications.findByAttrAndJoin(attrs, Arrays.asList("heading")));
        System.out.println(heading);
    }
}
