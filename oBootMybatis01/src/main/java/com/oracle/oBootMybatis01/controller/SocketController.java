package com.oracle.oBootMybatis01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SocketController {

	// Model, View 같이 만들 때 : ModelAndView 사용
	
	@RequestMapping("/chat")
	public ModelAndView chat() {
		
		System.out.println("SocketController chat() Start!");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("chatView");
		
		return mv;
		
		// chatView.jsp 를 열어줌
		
	}
	
}
