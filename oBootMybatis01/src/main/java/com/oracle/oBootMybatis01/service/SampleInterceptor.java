package com.oracle.oBootMybatis01.service;

import java.lang.reflect.Method;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SampleInterceptor implements HandlerInterceptor {

	public SampleInterceptor() {
		// TODO Auto-generated constructor stub
	}
	
	// intercept 순서 3번
	// intercept 되면 가장 나중에 수행 되는 놈
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		System.out.println("postHandle.....................................");
		
		String ID = (String) modelAndView.getModel().get("id");
		
		int memCnt = (int) modelAndView.getModel().get("memCnt");
		
		System.out.println("SampleInterceptor postHandle() memCnt : " + memCnt);
		
		if (memCnt < 1) {
			
			System.out.println("memCnt 존재하지 않음");
			
			request.getSession().setAttribute("ID", ID);
			
			// 유저가 존재하지 않으면 User InterCeptor Page (회원등록) 로 이동
			response.sendRedirect("doMemberWrite");
			
		} else {
			
			System.out.println("memCnt 존재함");
			
			request.getSession().setAttribute("ID", ID);
			
			// 유저가 존재하면 User InterCeptor Page (회원 List) 로 이동
			response.sendRedirect("doMemberList");
			
		}
	
	}
	
	// intercept 순서 1번
	// intercept 되면 가장 먼저 수행 되는 놈
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("preHandle.....................................");
		
		HandlerMethod method = (HandlerMethod) handler;
		
		Method methodObj = method.getMethod();
		
		System.out.println("Bean : " + method.getBean());
		System.out.println("Method : " + methodObj);
		
		return true;
	}
	
}
