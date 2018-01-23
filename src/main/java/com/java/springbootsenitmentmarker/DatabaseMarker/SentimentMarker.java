package com.java.springbootsenitmentmarker.DatabaseMarker;

import com.java.springbootsenitmentmarker.dao.TwitterRepository;
import com.java.springbootsenitmentmarker.entity.Twitter;
import com.java.springbootsenitmentmarker.util.CoreNLP;
import com.java.springbootsenitmentmarker.util.SentimentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SentimentMarker {

    @Autowired
    TwitterRepository twitterRepository;
    private void SentimentTotalUpdate() {
        ArrayList<String> tweets = new ArrayList<String>();
        long startTime = System.currentTimeMillis(); //获取开始时间
        CoreNLP sentimentAnalyzer = new CoreNLP();
        sentimentAnalyzer.init();
        boolean notEmpty=true;
        int page=0;
        while(notEmpty){
            Pageable pageable = new PageRequest(page, 1000);
            Page<Twitter> TwitterPage=twitterRepository.findAll(pageable);
            if(TwitterPage ==null ){notEmpty=false;}
            page++;
            Iterator<Twitter> it=TwitterPage.iterator();
            while(it.hasNext()){
                Twitter tweet=it.next();
                SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(tweet.getContent());
                tweet.setSentimentScore(sentimentResult.getSentimentType());
                twitterRepository.save(tweet);
            }
        }
    }
    private void SentimentPartialUpdate() {
        ArrayList<String> tweets = new ArrayList<String>();
        long startTime = System.currentTimeMillis(); //获取开始时间
        CoreNLP sentimentAnalyzer = new CoreNLP();
        sentimentAnalyzer.init();
        int i=0,num=50;
        for (Twitter tweet : twitterRepository.findBySentimentScoreIsNotNull()) {
            SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(tweet.getContent());
            tweet.setSentimentScore(sentimentResult.getSentimentType());
            twitterRepository.save(tweet);
            //int score=CoreNLP.findSentiment(tweet.getContent());
//            tweets.add(i + "--" + tweet.getContent() +
//                    " -- Sentiment Score:" + sentimentResult.getSentimentScore() +
//                    " -- Sentiment Type: " + sentimentResult.getSentimentType() +
//                    " -- Very positive: " + sentimentResult.getSentimentClass().getVeryPositive() + "%" +
//                    " -- Positive: " + sentimentResult.getSentimentClass().getPositive() + "%" +
//                    " -- Neutral: " + sentimentResult.getSentimentClass().getNeutral() + "%" +
//                    " -- Negative: " + sentimentResult.getSentimentClass().getNegative() + "%" +
//                    " -- Very negative: " + sentimentResult.getSentimentClass().getVeryNegative() + "%" +
//                    " -- Sentiment Strength: " + sentimentAnalyzer.getSentiStrength(tweet.getContent())
//            );
//            if (i == num) {
//                break;
//            }
//            i++;
        }
    }
    private void SentimentUpdateAfterId(String tweetId) {
        ArrayList<String> tweets = new ArrayList<String>();
        long startTime = System.currentTimeMillis(); //获取开始时间
        CoreNLP sentimentAnalyzer = new CoreNLP();
        sentimentAnalyzer.init();
        int i=0,num=50;
        for (Twitter tweet : twitterRepository.findByIdGreaterThan(Integer.valueOf(tweetId))) {
            SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(tweet.getContent());
            tweet.setSentimentScore(sentimentResult.getSentimentType());
            twitterRepository.save(tweet);
            //int score=CoreNLP.findSentiment(tweet.getContent());
//            tweets.add(i + "--" + tweet.getContent() +
//                    " -- Sentiment Score:" + sentimentResult.getSentimentScore() +
//                    " -- Sentiment Type: " + sentimentResult.getSentimentType() +
//                    " -- Very positive: " + sentimentResult.getSentimentClass().getVeryPositive() + "%" +
//                    " -- Positive: " + sentimentResult.getSentimentClass().getPositive() + "%" +
//                    " -- Neutral: " + sentimentResult.getSentimentClass().getNeutral() + "%" +
//                    " -- Negative: " + sentimentResult.getSentimentClass().getNegative() + "%" +
//                    " -- Very negative: " + sentimentResult.getSentimentClass().getVeryNegative() + "%" +
//                    " -- Sentiment Strength: " + sentimentAnalyzer.getSentiStrength(tweet.getContent())
//            );
//            if (i == num) {
//                break;
//            }
//            i++;
        }
    }
}
