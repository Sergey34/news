package net.news.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Menu {
    private String url;
    private String name;
}
