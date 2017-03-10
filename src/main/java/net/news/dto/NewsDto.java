package net.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.news.domain.news.Heading;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {
    private long id;
    private Date date;
    private String title;
    private String author;
    private List<Heading> heading;
    private String anons;
    private String text;
}
