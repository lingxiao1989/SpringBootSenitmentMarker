package com.java.springbootsenitmentmarker.util;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;
import uk.ac.wlv.sentistrength.SentiStrength;

import java.util.Properties;

public class CoreNLP {
    static StanfordCoreNLP pipeline;

    public static void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public SentimentResult getSentimentResult(String text) {
        SentimentResult sentimentResult = new SentimentResult();
        SentimentClassification sentimentClass = new SentimentClassification();
        if (text != null && text.length() > 0) {
            // run all Annotators on the text
            Annotation annotation = pipeline.process(text);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                // this is the parse tree of the current sentence
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
                String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
                sentimentClass.setVeryPositive((double)Math.round(sm.get(4) * 100d));
                sentimentClass.setPositive((double)Math.round(sm.get(3) * 100d));
                sentimentClass.setNeutral((double)Math.round(sm.get(2) * 100d));
                sentimentClass.setNegative((double)Math.round(sm.get(1) * 100d));
                sentimentClass.setVeryNegative((double)Math.round(sm.get(0) * 100d));
                sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));
                sentimentResult.setSentimentType(sentimentType);
                sentimentResult.setSentimentClass(sentimentClass);
            }
        }
        return sentimentResult;
    }
    public String getSentiStrength(String text) {
        SentiStrength sentiStrength = new SentiStrength();
        String ssthInitialisation[] = {"sentidata", "lib/sentistrength/SentStrength_Data_December2015English/", "explain"};
        sentiStrength.initialise(ssthInitialisation); //Initialise

//        String content = "I love the products provided by Apple Inc.";

        String emoRes = "0  0   0";
        if(text.length()>0) {
            String res = sentiStrength.computeSentimentScores(text);
            emoRes = extractEmotionRes(res);
        }
        return emoRes;
    }
    private String extractEmotionRes(String res){
        if(res == null || res.length() ==0) {
            //String msg = "ERROR! input is empty.";
            //throw new Exception(msg);
            return "0   0   0";
        }

        int nBeg = res.lastIndexOf("[");
        if(nBeg == -1) {
            res = res.replaceAll(" ", "\t");
            res += "    0";
            return res;
        }


        String[] array = res.split(" ");
        String posVal = array[0];
        String negVal = array[1];

        String preFix = " result = ";
        nBeg = res.indexOf(preFix, nBeg);
        nBeg = nBeg+preFix.length();
        int nEnd = res.indexOf(" ", nBeg);
        String emoRes = res.substring(nBeg, nEnd);
        if(emoRes.equals("largest")) emoRes = "0";
        return posVal + "   " + negVal + "  " + emoRes;
    }

//    public static int findSentiment(String tweet) {
//        int mainSentiment = 0;
//        if (tweet != null && tweet.length() > 0) {
//            int longest = 0;
//            Annotation annotation = pipeline.process(tweet);
//            for (CoreMap sentence : annotation
//                    .get(CoreAnnotations.SentencesAnnotation.class)) {
//                Tree tree = sentence
//                        .get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
//                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
//                SimpleMatrix sentiment_new = RNNCoreAnnotations.getPredictions(tree);
//                String partText = sentence.toString();
//                if (partText.length() > longest) {
//                    mainSentiment = sentiment;
//                    longest = partText.length();
//                }
//            }
//        }
//        return mainSentiment;
//    }
}
