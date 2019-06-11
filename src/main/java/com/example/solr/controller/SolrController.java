package com.example.solr.controller;

import com.example.solr.domain.Event;
import com.example.solr.service.SolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SolrController {

    @Autowired
    private SolrService solrService;

    @PostMapping("/queryEvent")
    public List<Event> queryEvent(@RequestBody Map<String, String> map) {
        System.out.println(solrService.queryEventDocument(map));
        return solrService.queryEventDocument(map);
    }
}
