package net.news.service;

import net.news.dao.DaoRole;
import net.news.dao.DaoUser;
import net.news.dto.UserDto;
import net.news.service.converters.ConverterUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
