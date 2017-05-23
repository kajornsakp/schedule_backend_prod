package com.example.controller;

import com.example.config.MongoConfig;
import com.example.model.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;

/**
 * Created by ShubU on 5/23/2017 AD.
 */
public class ExternalDataHandler {

    public static ArrayList<String> getOtherByID(Subject subject){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoOperations mongoOperations = (MongoOperations) applicationContext.getBean("mongoTemplate");

        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0 ; i < subject.getLecturerList().size() ; i++){
            ArrayList<String> subjectList = subject.getLecturerList().get(i).getSubjects();
            for (int j = 0 ; j < subjectList.size(); j++){
                Subject s = mongoOperations.findOne(new Query(Criteria.where("name").is(subjectList.get(j))), Subject.class);
                result.add(s.getId());
            }
        }
        return result;
    }
}
