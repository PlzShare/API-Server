package com.douzone.weboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.weboard.interceptor.JwtInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private Environment env;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		System.out.println(env.getProperty("fileupload.resourceMapping"));
		System.out.println(env.getProperty("fileupload.fileUploadLocation")); 
		
		registry
			.addResourceHandler(env.getProperty("fileupload.resourceMapping"))
			.addResourceLocations(env.getProperty("fileupload.fileUploadLocation"));
	}	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	 
	 private static final String[] EXCLUDE_PATHS = {
	            "/error/**"
	    };

	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(jwtInterceptor)
	                .addPathPatterns("/**")
	                .excludePathPatterns("/users/login")
	                .excludePathPatterns(EXCLUDE_PATHS);
	    }
	
	
	
}
