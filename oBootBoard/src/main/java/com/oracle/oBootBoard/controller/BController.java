package com.oracle.oBootBoard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootBoard.command.BExecuteCommand;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BController {
	
	private static final Logger logger = LoggerFactory.getLogger(BController.class);

	// @Controller에 @Service 연결해주자
		// BExecuteCommand = @Service
	private final BExecuteCommand bExecuteCommand;
	// @Service에 DAO 연결해주자
	@Autowired
	public BController(BExecuteCommand bExecuteCommand) {
		this.bExecuteCommand = bExecuteCommand;
	}
	
	// 게시판 목록
	@RequestMapping("list")
	public String list(Model model) {
		
		logger.info("list start...");
		
		bExecuteCommand.bListCmd(model);
		
		return "list";
		
	}
	
	// 글작성
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		
		logger.info("write_view start...");
		
		return "write_view";
		
	}
	
	// write_view
	@PostMapping(value = "/write")
	public String write(HttpServletRequest request, Model model) {
		
		logger.info("write start...");
		
		// Model이가 key + value 쌍을 가짐
		model.addAttribute("request", request);
		
		bExecuteCommand.bWriteCmd(model);
		
		return "redirect:list";
		
	}
	
	// content_view
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		
		logger.info("content_view()");
		
		model.addAttribute("request", request);
		
		bExecuteCommand.bContentCmd(model);
		
		return "content_view";
		
	}
	
}
