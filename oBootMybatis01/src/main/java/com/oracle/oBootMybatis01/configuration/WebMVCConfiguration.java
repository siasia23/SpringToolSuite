package com.oracle.oBootMybatis01.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oracle.oBootMybatis01.service.SampleInterceptor;

@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 누군가가 URL에 /interCeptor 치면 SampleInterceptor() 처리해줌
		registry.addInterceptor(new SampleInterceptor()).addPathPatterns("/interCeptor");
		
	}
	
}
