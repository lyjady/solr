package com.example.solr.dao.impl;

import com.example.solr.dao.SolrDao;
import com.example.solr.domain.Event;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class SolrDaoImpl implements SolrDao {

    private SolrClient solrClient = new HttpSolrClient.Builder("http://localhost:8081/solr/db").build();

    @Override
    public List<Event> queryEventDocument(Map<String, String> map) throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();

        boolean titleIsNull = false;

        solrQuery.setRows(10);
        solrQuery.set("df", "eventTitle");
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if ("eventTitle".equals(entry.getKey())) {
                if (map.get(entry.getKey()) != null && !"".equals(map.get(entry.getKey()))) {
                    solrQuery.set("q", entry.getKey() + ":" + entry.getValue());
                    solrQuery.addHighlightField(entry.getKey());
                }
            } else if ("eventContents".equals(entry.getKey())) {
                if (map.get(entry.getKey()) != null && !"".equals(map.get(entry.getKey()))) {
                    solrQuery.set("q", entry.getKey() + ":" + entry.getValue());
                    solrQuery.addHighlightField(entry.getKey());
                    titleIsNull = true;
                }
            } else if (!"".equals(map.get(entry.getKey()))) {
                solrQuery.addFilterQuery(entry.getKey() + ":" + entry.getValue());
            }
        }

        QueryResponse response = solrClient.query(solrQuery);
        SolrDocumentList results = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        List<Event> events = new ArrayList<>();
        Event event = null;

        for (SolrDocument document : results) {
            event = new Event();
            event.setId((String) document.get("id"));
            if (titleIsNull) {
                Map<String, List<String>> listMap = highlighting.get(document.get("id"));
                event.setEventContent(listMap.get("eventContents").get(0));
                event.setEventTitle((String) document.get("eventTitle"));
            } else {
                Map<String, List<String>> listMap = highlighting.get(document.get("id"));
                event.setEventTitle(listMap.get("eventTitle").get(0));
                event.setEventContent((String) document.get("eventContents"));
            }
            event.setEventAnalysis((String) document.get("eventAnalysis"));
            event.setEventLevel((String) document.get("eventLevel"));
            event.setEventTime((Date) document.get("eventTime"));
            event.setEventWay((String) document.get("eventWay"));
            event.setAreaId((String) document.get("areaId"));
            events.add(event);
        }
        return events;
    }
}
