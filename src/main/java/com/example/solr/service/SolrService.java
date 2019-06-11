package com.example.solr.service;

import com.example.solr.domain.Event;

import java.util.List;
import java.util.Map;

public interface SolrService {
    List<Event> queryEventDocument(Map<String, String> map);
}
