package net.news.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * рубрика
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Heading {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String name;
}
