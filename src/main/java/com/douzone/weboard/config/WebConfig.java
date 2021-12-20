package com.douzone.weboard.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.weboard.interceptor.JwtInterceptor;
import com.douzone.weboard.util.AuthUserArgumentResolver;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		System.out.println(env.getProperty("fileupload.resourceMapping"));
		System.out.println(env.getProperty("fileupload.fileUploadLocation"));

		registry.addResourceHandler(env.getProperty("fileupload.resourceMapping"))
				.addResourceLocations(env.getProperty("fileupload.fileUploadLocation"));
	}

	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/users/login","/users/join");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// TODO Auto-generated method stub
		System.out.println("Config000000000000000000000000000000000000000000000000000");
		resolvers.add(new AuthUserArgumentResolver());
	}

	
}
