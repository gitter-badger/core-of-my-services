package com.nesterenya.config;

import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.nesterenya.Application;

//@PropertySource("classpath:mongodb.properties")

//@Configuration
//@ComponentScan("com.nesterenya")
//@EnableMongoRepositories(basePackages="com.nesterenya.repository")
//@ComponentScan(basePackageClasses = Application.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)

@Configuration
//@EnableMongoRepositories
@ComponentScan("com.nesterenya")
public class SpringMongoConfig extends AbstractMongoConfiguration {
    
	//@Inject
  //  @Autowired
//	Environment environment;
    
    @Override
    public String getDatabaseName() {
        return "journeydb";//environment.getProperty("db.name");
    }
    

    @Override
    @Bean
    public Mongo mongo() throws Exception {
    	//System.out.println("sdf");
       // return new MongoClient("127.0.0.1");
    	return new MongoClient();
    }
    
    
/*
    @Override
    protected String getMappingBasePackage() {
        return "com.nesterenya.repository";
    }*/
}