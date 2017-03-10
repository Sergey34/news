package net.news.service;

import net.news.domain.news.Heading;
import net.news.dto.Menu;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {
    public List<Menu> headingsToMenu(Collection<Heading> headings) {
        return headings.stream().map(this::headingToMenu).collect(Collectors.toList());
    }

    private Menu headingToMenu(Heading heading) {
        return Menu.builder().name(heading.getName())
                .url("/" + heading.getName()).build();
    }
}
