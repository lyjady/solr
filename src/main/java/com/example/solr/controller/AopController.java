package com.example.solr.controller;

import com.example.solr.service.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AopController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AopService aopService;

    @RequestMapping("/aop")
    public void aop() throws Exception {
        session.setAttribute("name", "jackson");
        aopService.aopApplcation();
    }

    @RequestMapping("/name")
    public void getName() {
        System.out.println(session.getAttribute("name"));
    }
}
