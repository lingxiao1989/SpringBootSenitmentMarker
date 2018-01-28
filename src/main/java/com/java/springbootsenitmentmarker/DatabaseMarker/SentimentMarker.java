package com.java.springbootsenitmentmarker.DatabaseMarker;

import com.java.springbootsenitmentmarker.dao.TwitterRepository;
import com.java.springbootsenitmentmarker.entity.twitter_info;
import com.java.springbootsenitmentmarker.util.CoreNLP;
import com.java.springbootsenitmentmarker.util.SentimentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SentimentMarker implements ApplicationRunner {
    static int pageSize=15;
    static int startPage=0;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        while(true) {
            try {
                boolean UpdateAll = args.containsOption("UpdateAll");
                boolean StartAfterId = args.containsOption("StartAfter");

                if(UpdateAll) {
                    SentimentTotalUpdate();
                }
                else if(StartAfterId) {
                    List<String> autoId = args.getOptionValues("StartAfter");
                    if(!autoId.isEmpty()){
                        SentimentUpdateAfterId(autoId.get(0));
                    }
                }
                else{
                    SentimentPartialUpdate();
                }
                System.out.println("运行结束,休息20分钟");
                Thread.sleep(1000 * 60 * 20);
            } catch (Exception e) {
                System.out.println("Got a Exception：" + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
//        for (String name : args.getOptionNames()){
//            System.out.println("arg-" + name + "=" + args.getOptionValues(name));
//        }
    }

    @Autowired
    TwitterRepository twitterRepository;
    private void SentimentTotalUpdate() {
        ArrayList<String> tweets = new ArrayList<String>();
        long startTime = System.currentTimeMillis(); //获取开始时间
        CoreNLP sentimentAnalyzer = new CoreNLP();
        sentimentAnalyzer.init();
        int currentPage=startPage;
        while(true){
            Pageable pageable = new PageRequest(currentPage, pageSize);
            Page<twitter_info> TwitterPage=twitterRepository.findAll(pageable);
            //if(!TwitterPage.hasContent()||TwitterPage.getContent()==null ){break;}
            currentPage++;
            Iterator<twitter_info> it=TwitterPage.iterator();
            while(it.hasNext()){
                twitter_info tweet=it.next();
                SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(tweet.getText());
                tweet.setSupportLevel(sentimentResult.getSentimentType());
                twitterRepository.save(tweet);
            }
            if(TwitterPage.isLast()){break;}
        }
    }
    private void SentimentPartialUpdate() {
        ArrayList<String> tweets = new ArrayList<String>();
        long startTime = System.currentTimeMillis(); //获取开始时间
        CoreNLP sentimentAnalyzer = new CoreNLP();
        sentimentAnalyzer.init();
        int currentPage=startPage;
        while(true){
            Pageable pageable = new PageRequest(currentPage, pageSize);
            Page<twitter_info> TwitterPage=twitterRepository.findBySupportLevelIsNull(pageable);
            currentPage++;
            Iterator<twitter_info> it=TwitterPage.iterator();
            while(it.hasNext()){
                twitter_info tweet=it.next();
                SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(tweet.getText());
                tweet.setSupportLevel(sentimentResult.getSentimentType());
                twitterRepository.save(tweet);
            }
            if(TwitterPage.isLast()){break;}
        }
    }
    private void SentimentUpdateAfterId(String autoId) {
        ArrayList<String> tweets = new ArrayList<String>();
        long startTime = System.currentTimeMillis(); //获取开始时间
        CoreNLP sentimentAnalyzer = new CoreNLP();
        sentimentAnalyzer.init();
        int currentPage=startPage;
        while(true){
            Pageable pageable = new PageRequest(currentPage, pageSize);
            Page<twitter_info> TwitterPage=twitterRepository.findByAutoIdGreaterThan((Integer.valueOf(autoId)),pageable);
            //if(!TwitterPage.hasContent()||TwitterPage.getContent()==null ){break;}
            currentPage++;
            Iterator<twitter_info> it=TwitterPage.iterator();
            while(it.hasNext()){
                twitter_info tweet=it.next();
                SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(tweet.getText());
                tweet.setSupportLevel(sentimentResult.getSentimentType());
                twitterRepository.save(tweet);
            }
            if(TwitterPage.isLast()){break;}
        }
//        for (twitter_info tweet : twitterRepository.findByAutoIdGreaterThan(Integer.valueOf(tweetId))) {
//            SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(tweet.getText());
//            tweet.setSupportLevel(sentimentResult.getSentimentType());
//            twitterRepository.save(tweet);
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
//        }
    }
}
