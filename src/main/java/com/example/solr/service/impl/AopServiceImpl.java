package com.example.solr.service.impl;

import com.example.solr.exception.Methiod;
import com.example.solr.service.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;

@Service
public class AopServiceImpl implements AopService {

    @Autowired
    private HttpSession session;

    @Override
    public void aopApplcation() throws Exception {

        System.out.println("AopServiceImpl.aopApplcation" + session.getAttribute("name"));

            new Methiod().method();


    }
}
