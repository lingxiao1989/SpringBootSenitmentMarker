package com.java.springbootsenitmentmarker.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class twitter_info {
    @Id
    @GeneratedValue
    private  Integer id;
    private String content;
    private Integer sentimentScore;
    private String twitterId;
    public twitter_info() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Integer sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }
}
