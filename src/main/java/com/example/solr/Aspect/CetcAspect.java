package com.example.solr.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Aspect
public class CetcAspect {

    @Autowired
    private HttpSession session;

    @Pointcut("execution(* com.example.solr.controller.AopController.*(..))")
    public void pointCut() {

    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "exception")
    public void textException(JoinPoint point, Throwable exception) {
        System.out.println("==========================================");
        System.out.println("错误信息" + exception.getMessage());
        System.out.println(point.getSignature().getName());
        System.out.println(point.getTarget().getClass().toString());
        System.out.println("name" + session.getAttribute("name"));
        System.out.println("==========================================");
//        System.out.println("错误方法");
//        System.out.println("错误类名");
    }
}
