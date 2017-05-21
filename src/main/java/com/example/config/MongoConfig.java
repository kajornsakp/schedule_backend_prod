package com.example.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Arrays;


@Configuration
@EnableMongoRepositories
public class MongoConfig{
    public @Bean
    MongoDbFactory mongoDbFactory() throws Exception {
//        return new SimpleMongoDbFactory(new MongoClient(), "ICSpark");
        MongoCredential credential = MongoCredential.createCredential("schedule","schedule","schedule".toCharArray());
        ServerAddress serverAddress = new ServerAddress("188.166.187.184",27017);

        MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient,"schedule");
        return simpleMongoDbFactory;
    }

    @Bean
    public DispatcherServlet dispatcherServlet(){
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setDispatchOptionsRequest(true);
        return servlet;
    }

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;

    }
}

 