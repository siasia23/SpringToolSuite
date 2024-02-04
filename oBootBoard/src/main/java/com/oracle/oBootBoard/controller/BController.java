package com.oracle.oBootBoard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oracle.oBootBoard.command.BExecuteCommand;

import jakarta.servlet.http.HttpServletRequest;

//@Service가 @Controller를 찾음
// DispatcherServlet이 controller를 찾을 때 @Controller 있는 놈을 찾음

// @Controller : using @Service methods, return View name

// DispatcherServlet   : Front Controller.
//								: HTTP request 받아들여서 다른 스프링 내장객체들 사이의 흐름 제어.

@Controller
public class BController {
	
	// 콘솔에 로그 찍자
	private static final Logger logger = LoggerFactory.getLogger(BController.class);

	// @Controller에 @Service 연결해주자
		// BExecuteCommand = @Service
	private final BExecuteCommand bExecuteCommand;
	
	// @Autowired : Spring Container에서 해당되는(의존성 있는) Bean(Java 객체)을 찾아서 연결(의존성 주입)함.

	// @Service에 DAO 연결해주자 (DI방식)
		// BController = @Controller
	@Autowired	
	public BController(BExecuteCommand bExecuteCommand) {
		this.bExecuteCommand = bExecuteCommand;
	}
	
	// @RequestMapping : Annotation for mapping web requests onto methods.
	// 이것보다는 @GetMapping, @PostMapping이 권장됨
	
	// Controller에서는 Model 써서 data를 갖고다녀라. 
		// request.setAttribute() 대신에.
		// Model이 이미 attribute(key) 갖고 있으니까.
	
	// DI 방식을 썼기 때문에 BExecuteCommand bExecuteCommand = new BExecuteCommand(); 할 필요가 없어짐
	
	// list
	// 게시판 목록
	@RequestMapping("list")
	public String list(Model model) {
		
		logger.info("list start...");
		
		// model이는 객체니까 call by reference(값이 아니라 주소값을 넘겨줌. 객체를 넣어줌). (call by value(값을 직접 넘겨줌)가 아님)
		// . 찍어서 나오는건 전부 객체임.
		// call by reference는 변경된 value 유지함.
		// 그래서 여기서 setAttribute 안 해줘도, service에서 변경해줬던 value 그대로 유지해서 가져옴
		bExecuteCommand.bListCmd(model);
		
		return "list";
		
	}
	
	// write_view 페이지로 가기
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		
		logger.info("write_view start...");
		
		return "write_view";
		
	}
	
	// write_view
	// 새 게시글 작성하기
	@PostMapping(value = "/write")
	public String write(HttpServletRequest request, Model model) {
		
		logger.info("write start...");
		
		// Model이가 key + value 쌍을 가짐
			// 여기서 key 	= String request
			// 여기서 value = Object request = HttpServletRequest
		model.addAttribute("request", request);
		
		// Service method 호출하면서 객체 Model 전달해주기
		bExecuteCommand.bWriteCmd(model);
		
		// View Resolver에게 전달할 view name을 return
		return "redirect:list";

		// View Resolver는 전달받은 view name과 prefix, suffix 조립해서 client에게 던져줄(보여줄) view file 결정함.
		
	}
	
	// content_view
	// 게시글 클릭하면 해당 내용 뿌리기
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		
		logger.info("content_view()");
		
		model.addAttribute("request", request);
		
		bExecuteCommand.bContentCmd(model);
		
		return "content_view";
		
	}
	
	// content_view에서
	// 작성된 게시글 수정하기
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) {
		
		logger.info("modify start...");
		
		// View에 전달할 data를 Model에 저장
		model.addAttribute("request", request);
		
		bExecuteCommand.bModifyCmd(model);
		
		return "redirect:list";
		
	}
	
	// reply_view
	// 댓글 입력 창
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		
		System.out.println("BController reply_view start...");
		
		model.addAttribute("request", request);
		
		bExecuteCommand.bReplyViewCmd(model);
		
		return "reply_view";
		
	}
	
	// reply_view
	// 댓글 쓰기
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(HttpServletRequest request, Model model) {
		
		System.out.println("BController reply start...");
		
		model.addAttribute("request", request);
		
		bExecuteCommand.bReplyCmd(model);
		
		return "redirect:list";
		
	}
	
	// 글 삭제
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		
		System.out.println("BController delete start...");
		
		model.addAttribute("request", request);
		
		bExecuteCommand.bDeleteCmd(model);
		
		return "redirect:list";
		
	}
	
}
