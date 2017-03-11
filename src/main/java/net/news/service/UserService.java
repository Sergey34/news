package net.news.service;

import net.news.dao.DaoRole;
import net.news.dao.DaoUser;
import net.news.domain.users.Role;
import net.news.domain.users.User;
import net.news.dto.UserDto;
import net.news.service.converters.ConverterUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return converter.userToUserDto(daoUser.findOneByLogin(login));
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

    public List<UserDto> gitAllUsers(String column, String sort) {
        if (column == null || sort == null) {
            return converter.userToUserDto(daoUser.findAll());
        }
        return "asc".equals(sort) ?
                converter.userToUserDto(daoUser.findAll(new Sort(Sort.Direction.DESC, column))) :
                converter.userToUserDto(daoUser.findAll(new Sort(Sort.Direction.ASC, column)));
    }

    public boolean addUser(String login, String name, String pass, List<String> roles, String email) {
        if (daoUser.findOneByLogin(login) != null) {
            return false;
        }
        User user = User.builder()
                .login(login)
                .password(new BCryptPasswordEncoder().encode(pass))
                .email(email)
                .roles(getRoleByRoleName(roles))
                .name(name).build();
        daoUser.save(user);
        return false;
    }

    private Set<Role> getRoleByRoleName(List<String> roles) {
        Set<Role> rolesResult = new HashSet<>();
        if (roles == null) {
            return rolesResult;
        }
        for (String role : roles) {
            for (Role role1 : getAllRoles()) {
                if (role.equals(role1.getAuthority())) {
                    rolesResult.add(role1);
                }
            }
        }
        return rolesResult;
    }

    public List<Role> getAllRoles() {
        return daoRole.findAll();
    }

    public UserDto getUser(String login) {
        User currentUser;
        if (login == null) {
            currentUser = getCurrentUser();
        } else {
            currentUser = daoUser.findOneByLogin(login);
        }
        return converter.userToUserDto(currentUser);
    }

    public String getLoginCurrentUser() {
        User currentUser = getCurrentUser();
        return currentUser == null ? null : currentUser.getLogin();
    }

    public boolean update(String login, String name, String pass, List<String> roles, String email, String passOld) {
        User user = daoUser.findOneByLogin(login);
        if (user.getPassword().equals(new BCryptPasswordEncoder().encode(passOld))) {
            user.setName(name);
            user.setPassword(new BCryptPasswordEncoder().encode(pass));
            user.setEmail(email);
            user.setLogin(login);
            user.setRoles(getRoleByRoleName(roles));
            daoUser.save(user);
            return true;
        }
        return false;
    }


    public boolean currentUserIsAdmin() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        for (Role role : currentUser.getRoles()) {
            if ("ROLE_ADMIN".equals(role.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}

