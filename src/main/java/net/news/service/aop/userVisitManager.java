package net.news.service.aop;

import net.news.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class userVisitManager {
    private final UserService userService;

    @Autowired
    public userVisitManager(UserService userService) {
        this.userService = userService;
    }

    @Before("execution(* net.news.controllers.ControllerNews.*(..))")
    public void log(JoinPoint joinPoint) {
        userService.updateLastDateVisit();
    }
}
