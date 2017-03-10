package net.news.service;

import net.news.domain.users.User;
import net.news.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class ConverterUsers {
    public UserDto UserToUserDto(User user) {
        return UserDto.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .name(user.getName())
                .roles(user.getRoles()).build();
    }
}
