package net.news.domain;

import net.news.domain.users.User;

/**
 * Created by seko0716 on 3/13/2017.
 */
public interface Projected {
    User getAuthor();

    String getTitle();
}
