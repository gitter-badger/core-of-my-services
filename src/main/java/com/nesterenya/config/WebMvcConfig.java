package com.nesterenya.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.nesterenya.Application;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackageClasses = Application.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)
public class WebMvcConfig extends WebMvcConfigurationSupport{
	private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
    private static final String RESOURCES_LOCATION = "/resources/";
    private static final String RESOURCES_HANDLER = RESOURCES_LOCATION + "**";

/*
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        //resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        return resolver;
    }*/


    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE);
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
