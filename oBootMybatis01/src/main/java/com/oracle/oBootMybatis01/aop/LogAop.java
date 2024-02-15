package com.oracle.oBootMybatis01.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAop {
/*
	// oBootMybatis01.dao.EmpDao 패키지 안에 있는 모든 메소드를 실행시킬 때
	@Pointcut("within(com.oracle.oBootMybatis01.dao.*)")
	private void pointcutMethod() {
		
	}
	
	// around 방식 : 시작과 끝을 모두 보여줘서 성능 측정 가능
	@Around("pointcutMethod()")
	public Object loggerAop(ProceedingJoinPoint joinPoint) throws Throwable {
		
		// oBootMybatis01.dao.EmpDao 에서 수행 되는 핵심관심사
		String signatureStr = joinPoint.getSignature().toString();
		System.out.println(signatureStr + " is start!");
		
		long st = System.currentTimeMillis();
		
		try {
			
			// 핵심관심사
			Object obj = joinPoint.proceed();
			
			return obj;
			
		} finally {
			
			long et = System.currentTimeMillis();
			
			System.out.println(signatureStr + " is finished.");
			System.out.println(signatureStr + " 경과시간 : " + (et - st));
			
		}
		
	}
	
	@Before(value = "pointcutMethod()")
	public void before() throws Throwable {
			
			System.out.println(" Before.");
		
	}
*/	
}
