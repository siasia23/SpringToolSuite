package com.oracle.oBootMybatis01.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {		// 암호화 설정해보자

	@Bean
	public BCryptPasswordEncoder encodePwd() {		// 암호화 모듈
		
		return new BCryptPasswordEncoder();
	
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.cors(cors -> cors.disable())
			.csrf(csrf -> csrf.disable());
		
		return http.build();
		
	}
	
}
