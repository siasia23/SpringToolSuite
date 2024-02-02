package com.oracle.oBootBoard;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dao.JdbcDao;

//환경 설정
@Configuration	
public class SpringConfig {

	private final DataSource dataSource;
	
	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// @Configuration에 @Bean으로 설정해서 component화
	@Bean
	public BDao jdbcDao() {
		return new JdbcDao(dataSource);
	}
	
}
