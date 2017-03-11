package net.news.service.converters;

import net.news.domain.users.User;
import net.news.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverterUsers {
    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .name(user.getName())
                .lastVisit(user.getLastVisit())
                .roles(user.getRoles()).build();
    }

    public List<UserDto> userToUserDto(Collection<User> users) {
        return users.stream().map(this::userToUserDto).collect(Collectors.toList());
    }
}
