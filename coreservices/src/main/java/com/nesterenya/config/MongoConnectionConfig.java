package com.nesterenya.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Component
public class MongoConnectionConfig {
	
	@Value("${db.uri}")
	private String uri;
	
	@Value("${db.database}")
	private String database;
	
	public String getConnectionURI() {
		return uri;
	}
	
	public String getDataBase() {
		return database;
	}
	
	
	
	
}
