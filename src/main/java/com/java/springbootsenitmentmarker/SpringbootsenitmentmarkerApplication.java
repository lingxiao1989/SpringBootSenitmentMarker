package com.java.springbootsenitmentmarker;

import com.java.springbootsenitmentmarker.DatabaseMarker.SentimentMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootsenitmentmarkerApplication {

	public static void main(String[] args) throws Exception{
        SpringApplication.run(SpringbootsenitmentmarkerApplication.class, args);
	}
    @Bean
    public SentimentMarker sentimentMarker(){
        return new SentimentMarker();
    }

}
