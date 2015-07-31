package com.nesterenya.config;

import static org.springframework.context.annotation.ComponentScan.Filter;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.nesterenya.Application;
import com.nesterenya.scheduling.RunParser;

@Configuration
@EnableScheduling
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({Controller.class, Configuration.class}))
class ApplicationConfig {
	
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
	
	/*@Bean
	public RunParser task() {
		return new RunParser();
	}*/
}