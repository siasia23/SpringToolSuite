package com.oracle.oBootJpa01.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootJpa01.domain.Member;
import com.oracle.oBootJpa01.service.MemberService;

// 컨트롤러는 클라이언트의 요청값에 매핑되는 서비스를 찾아 수행하고 뷰 네임을 리턴

@Controller
public class MemberController {

	private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	private final MemberService memberService;
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	// createMemberForm.html
	@GetMapping(value = "/members/new")
	public String createForm() {
		
		System.out.println("MemberController /members/new start!");
		
		// 컨트롤러가 리턴하는건 view name
		return "members/createMemberForm";
		
	}
	
	// 회원가입
	// createMemberForm.html
	@PostMapping(value = "members/save")
	public String memberSave(Member member) {
		
		System.out.println("MemberController memberSave start!");
		
		System.out.println("member.getId() : " + member.getId());
		System.out.println("member.getName() : " + member.getName());
		
		memberService.memberSave(member);
		
		System.out.println("MemberController memberSave After..");
		
		return "redirect:/";
		
	}
	
	// 회원 목록 조회
	// memberList.html
	@GetMapping(value = "/members")
	public String listMember(Model model) {
		
		List<Member> memberList = memberService.getListAllMember();
		
		log.info("memberList.size() : " + memberList.size());
		
		model.addAttribute("members", memberList);
		
		return "members/memberList";
		
	}
	
	// 회원 검색
	// memberList.html
	@PostMapping(value = "members/search")
	public String memberSearch(Member member, Model model) {
		
		System.out.println("members/search member.getName() : " + member.getName());
		
		List<Member> memberList = memberService.getListSearchMember(member.getName());
		
		System.out.println("members/search memberList.size() : " + memberList.size());
		
		model.addAttribute("members", memberList);
		
		return "members/memberList";
		
	}
	
}
