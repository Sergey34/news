package net.news.service.tt;

import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CashebleLoader implements CashebleLoaderI {
    private Map<String, Integer> map = new HashMap<>();

    {
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);
        map.put("6", 6);
    }

    @Override
    @Cacheable(value = "addresses")
    public Integer get(String s, int i) {
        load(s);
        return map.get(s) + i;
    }

    @SneakyThrows
    private void load(String s) {
        System.out.println("Loading...");
        Thread.sleep(1000);
        System.out.println("Loaded");
    }

    @Override
    @CacheEvict(value = "addresses", allEntries = true)
    public void put(String s, int i) {
        map.put(s, i);
    }
}
