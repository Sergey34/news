package net.news.service.converters;

import net.news.domain.news.Heading;
import net.news.domain.news.News;
import net.news.dto.Menu;
import net.news.dto.NewsDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverterNews {
    public List<Menu> headingsToMenu(Collection<Heading> headings) {
        return headings.stream().map(this::headingToMenu).collect(Collectors.toList());
    }

    private Menu headingToMenu(Heading heading) {
        return Menu.builder().name(heading.getName())
                .url(heading.getName()).build();
    }

    public NewsDto newsToNewsDto(News news) {
        return NewsDto.builder()
                .id(news.getId())
                .title(news.getTitle())
                .anons(news.getAnons())
                .text(news.getText())
                .date(news.getDate())
                .author(news.getAuthor().getLogin())
                .heading(news.getHeading()).build();
    }

    public List<NewsDto> newsToNewsDto(List<News> newsList) {
        return newsList.stream().map(this::newsToNewsDto).collect(Collectors.toList());
    }
}
