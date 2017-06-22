package net.news.service;

import net.news.service.tt.CashebleLoaderI;
import net.news.service.tt.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = Config.class))
public class CacheableLoaderTest {
    @Autowired
    private CashebleLoaderI cashebleLoader;

    @Test
    public void get() throws Exception {
        Integer integer311 = cashebleLoader.get("4", 1);
        System.out.println(integer311);
        Integer integer = cashebleLoader.get("2", 1);
        System.out.println(integer);
        Integer integer2 = cashebleLoader.get("2", 2);
        System.out.println(integer2);
        cashebleLoader.put("2", 775);
        Integer integer3112 = cashebleLoader.get("4", 1);
        System.out.println(integer3112);

        Integer integer20 = cashebleLoader.get("2", 2);
        System.out.println(integer20);
        Integer integer21 = cashebleLoader.get("2", 1);
        System.out.println(integer21);
        Integer integer3 = cashebleLoader.get("3", 0);
        System.out.println(integer3);
        Integer integer30 = cashebleLoader.get("3", 0);
        System.out.println(integer30);
    }


}