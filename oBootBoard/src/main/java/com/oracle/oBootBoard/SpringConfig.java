package com.oracle.oBootBoard;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dao.JdbcDao;

// @Configuration : 환경 설정 : Spring Container에 Bean 직접 등록
	// 직접 등록 안 하면 Spring이 알아서 동일 패키지 내의 class들에 대해 components scanning

// 1. 여기에서 @Bean으로 등록한 것들이 하나씩 Spring Container에 생성됨 
	// key	= Bean's name	(= method name)
	// value	= Bean's object	(= returned instance)

// 2. Spring Container가 생성된 Bean들끼리의 의존관계 분석해서 자동으로 의존성 주입시켜줌 (DI)

@Configuration	
public class SpringConfig {

	private final DataSource dataSource;
	
	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// @Bean : Java 객체. Spring root container에 생성돼서, 어디서든 공유 가능함.
	
	// @Configuration에 @Bean으로 설정해서 component화
	@Bean
	public BDao jdbcDao() {
		
		// JdbcDao 객체를 Spring의 Bean으로 쓰겠다.
		return new JdbcDao(dataSource);
		
		// 여기서 BeanFactory에 저장되는 정보
			// Bean's name 	(key) 	: jdbcDao
			// Bean's object (value) 	: new JdbcDao(dataSource)
		
	}
	
}
