package com.example.solr.domain;

import java.util.Date;

public class Event {
    private String id;
    private String eventTitle;
    private String eventContent;
    private Date eventTime;
    private String position;
    private String eventWay;
    private String eventAnalysis;
    private String attachUrl;
    private String areaId;
    private String eventLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEventWay() {
        return eventWay;
    }

    public void setEventWay(String eventWay) {
        this.eventWay = eventWay;
    }

    public String getEventAnalysis() {
        return eventAnalysis;
    }

    public void setEventAnalysis(String eventOrigin) {
        this.eventAnalysis = eventOrigin;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventContent='" + eventContent + '\'' +
                ", eventTime=" + eventTime +
                ", position='" + position + '\'' +
                ", eventWay='" + eventWay + '\'' +
                ", eventOrigin='" + eventAnalysis + '\'' +
                ", attachUrl='" + attachUrl + '\'' +
                ", areaId='" + areaId + '\'' +
                ", eventLevel='" + eventLevel + '\'' +
                '}';
    }
}
