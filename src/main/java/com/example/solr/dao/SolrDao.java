package com.example.solr.dao;

import com.example.solr.domain.Event;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SolrDao {

    List<Event> queryEventDocument(Map<String, String> map) throws IOException, SolrServerException;
}
