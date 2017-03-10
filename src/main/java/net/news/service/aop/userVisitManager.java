package net.news.service.aop;

import net.news.dao.UserDao;
import net.news.domain.users.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class userVisitManager {
    private final UserDao userDao;

    @Autowired
    public userVisitManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Before("execution(* net.news.controllers.ControllerNews.*(..))")
    public void log(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            User user = userDao.findOneByLogin(name);
            if (user != null) {
                user.setLastVisit(new Date());
                userDao.save(user);
            }
        }

    }
}
