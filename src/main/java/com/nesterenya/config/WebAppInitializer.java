package com.nesterenya.config;

import java.util.EnumSet;

import javax.servlet.*;

import com.nesterenya.components.CrawlServlet;
import org.springframework.core.Conventions;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.nesterenya.components.SimpleCORSFilter;

public class WebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addFilter("cors",SimpleCORSFilter.class)
				.addMappingForUrlPatterns(null, false,
						"/*"
						/*"/journey/places/*",
						"/blog/comments/*",
						"/ads/rent/*",
						"/wish/*",
						"/upload/*",
						"/links/*",
						"/rent/*",
						"/images/*",
						"/resource/*"*/
						);

		// Enable AJAX snapshot
		// servletContext.addFilter("crawl", CrawlServlet.class)
		//		.addMappingForUrlPatterns(null, false, "/*");

		super.onStartup(servletContext);
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { ApplicationConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebMvcConfig.class };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		return new Filter[] { characterEncodingFilter };
	}
}
