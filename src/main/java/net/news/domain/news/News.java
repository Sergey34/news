package net.news.domain.news;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.news.domain.users.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Идентификатор новости;
 * Дата-время размещения новости;
 * Заголовок (текст не более 50 символов);
 * Автор (выбор из списка авторов)
 * Рубрика (выбор из списка рубрик)
 * Анонс (краткий текст не более 250 символов);
 * Текст новости (текст не более 65535 символов).
 */
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date date;
    @Column(length = 50*2)
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Heading> heading;
    @Column(length = 250*2)
    private String anons;
    @Column(length = 65535)
    private String text;
}
