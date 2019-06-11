package com.example.solr.service.impl;

import com.example.solr.dao.SolrDao;
import com.example.solr.domain.Event;
import com.example.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SolrServiceImpl implements SolrService {

    @Autowired
    private SolrDao solrDao;

    @Override
    public List<Event> queryEventDocument(Map<String, String> map) {
        try {
            return solrDao.queryEventDocument(map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
