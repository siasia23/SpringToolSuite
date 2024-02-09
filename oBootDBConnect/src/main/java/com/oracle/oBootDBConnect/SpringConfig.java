package com.oracle.oBootDBConnect;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootDBConnect.repository.JdbcMemberRepository;
import com.oracle.oBootDBConnect.repository.MemberRepository;

// @Configuration : spring container 설정 (Bean 모음집인가봄)

// may be processed by the Spring container 
// to generate bean definitions 
// and service requests for those beans at runtime.

	// Spring container : 스프링의 핵심 컴포넌트!! 자바 객체 관리자.

@Configuration		
public class SpringConfig {

	// JDBC로 DB 연결
	private final DataSource dataSource;
	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// 스프링에서는 자바 객체를 Bean 이라고 함.
	// spring container 에 등록된 객체가 Bean.
	
	// = new MemberRepository();
	// @Bean 해야 component 화 되어서 메모리에 올라감
	// DI 방식 위해서 사용
	@Bean
	public MemberRepository memberRepository() {
		return new JdbcMemberRepository(dataSource);
	}
	
}
