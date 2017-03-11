package net.news.service;

import net.news.dao.DaoRole;
import net.news.dao.DaoUser;
import net.news.domain.users.User;
import net.news.dto.UserDto;
import net.news.service.converters.ConverterUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final DaoUser daoUser;
    private final DaoRole daoRole;
    private final ConverterUsers converter;

    @Autowired
    public UserService(DaoUser daoUser, DaoRole daoRole, ConverterUsers converter) {
        this.daoUser = daoUser;
        this.daoRole = daoRole;
        this.converter = converter;
    }

    public UserDto findUserByLogin(String login) {
        return converter.UserToUserDto(daoUser.findOneByLogin(login));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return daoUser.findOneByLogin(authentication.getName());
    }

    public void updateLastDateVisit() {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            currentUser.setLastVisit(new Date());
            daoUser.save(currentUser);
        }
    }
}
