package com.oracle.oBootMybatis01.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.service.MemberJpaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberJpaController {

	// service 연결시키기
	private final MemberJpaService memberJpaService;
	
	@GetMapping(value = "/memberJpa/new")
	public String createForm() {
		
		System.out.println("MemberJpaController /memberJpa/new");
		return "memberJpa/createMemberForm";
		
	}
	
	@PostMapping(value = "/memberJpa/save")
	public String create(Member member) {
		
		System.out.println("MemberJpaController /memberJpa/save");
		
		System.out.println("member.getId() : " + member.getId());
		System.out.println("member.getName() : " + member.getName());
		
		memberJpaService.join(member);
		
		return "memberJpa/createMemberForm";
		
	}
	
	@GetMapping(value = "/members")
	public String listMember(Model model) {
		
		System.out.println("MemberJpaController listMember");
		
		List<Member> memberList = memberJpaService.getListAllMember();
		
		model.addAttribute("members", memberList);
		
		return "memberJpa/memberList";
		
	}
	
	@GetMapping(value = "/memberJpa/memberUpdateForm")
	public String memberUpdateForm(Member member1, Model model) {
		
		Member member = null;
		String rtnJsp = "";
		System.out.println("MemberJpaController memberUpdateForm Id : " + member1.getId());
		
		Optional<Member> maybeMember = memberJpaService.findById(member1.getId());
		
		if (maybeMember.isPresent()) {
			
			System.out.println("maybeMember is NOT null");
			
			member = maybeMember.get();
			
			model.addAttribute("member", member);
			
			rtnJsp = "memberJpa/memberModify";
			
		} else {
			
			System.out.println("maybeMember is NULL");
			
			model.addAttribute("message", "member가 존재하지 않으니 입력부터 하시죠");
			
			rtnJsp = "forward:/members";
			
		}
		
		return rtnJsp;
		
	}
	
	@PostMapping(value = "/memberJpa/memberUpdate")
	public String memberUpdate(Member member, Model model) {
		
		System.out.println("MemberJpaController memberUpdate Id : " + member.getId());
		System.out.println("MemberJpaController memberUpdate Name : " + member.getName());
		
		memberJpaService.memberUpdate(member);
		
		return "redirect:/members";
		
	}
	
}
