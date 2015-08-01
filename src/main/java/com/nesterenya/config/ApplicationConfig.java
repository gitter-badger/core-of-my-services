package com.nesterenya.config;

import static org.springframework.context.annotation.ComponentScan.Filter;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.nesterenya.Application;
import com.nesterenya.scheduling.RunParser;

import java.util.Date;

@Configuration
@EnableScheduling
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({Controller.class, Configuration.class}))
@PropertySource("classpath:mongodb_nocommit.properties")
class ApplicationConfig {

	@Autowired
	private Environment env;

	@Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("/mongodb_nocommit.properties"));	
		//ppc.setLocation(new ClassPathResource("/mongodb.properties"));
		return ppc;
	}
	
	@Bean(name = "multipartResolver")
	public static CommonsMultipartResolver commonsMultipartResolver(){
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setDefaultEncoding("utf-8");
	    multipartResolver.setMaxUploadSize(2*1024*1024);
	    return multipartResolver;
	}

	@Bean
	public Datastore storage() {
		String dbUri = env.getProperty("db.uri");
		String dbName = env.getProperty("db.database");

		MongoClientURI uri = new MongoClientURI(dbUri);
		MongoClient mongoClient = new MongoClient(uri);
		Morphia morphia = new Morphia();

		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("org.mongodb.morphia.example");

		Datastore storage = morphia.createDatastore(mongoClient, dbName);
		storage.ensureIndexes();

		return storage;
	}
	
	/*@Bean
	public RunParser task() {
		return new RunParser();
	}*/
}