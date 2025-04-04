package com.example.senturainterviewonline;

import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SenturaInterviewOnlineApplication {
    public static void main(String[] args) {
        SpringApplication.run(SenturaInterviewOnlineApplication.class, args);
    }
    @Bean
    OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }
}
