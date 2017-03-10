package net.news.service;

import net.news.dao.RoleDao;
import net.news.dao.UserDao;
import net.news.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final ConverterUsers converter;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, ConverterUsers converter) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.converter = converter;
    }

    public UserDto findUserByLogin(String login) {
        return converter.UserToUserDto(userDao.findOneByLogin(login));
    }
}
