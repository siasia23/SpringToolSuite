package com.oracle.oBootJpaApi01.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController		// : annotated with @Controller and @ResponseBody. 
							// 사용 목적 : Ajax + RestApi

@RequiredArgsConstructor

@Slf4j					// : Causes lombok to generate a logger field. 

public class JpaRestApiController {
	
	// controller에 필수인 service!!!
	private final MemberService memberService;
	
	// postman ---> Body --> raw---> JSON	 
    //  예시    {	    "name" : "kkk222"	    }
	
	// version 1
	@PostMapping("/restApi/v1/memberSave")
																// @RequestBody : Json(member)으로 온것을  --> Member member Setting
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		
		System.out.println("JpaRestApiController /restApi/v1/memberSave member.getId() : " + member.getId());
		
		log.info("member.getName() : {}.", member.getName());
		log.info("member.getSal() : {}.", member.getSal());
		
		Long id = memberService.saveMember(member);
		
		return new CreateMemberResponse(id);
		
	}
	
	// version 2
	@PostMapping("/restApi/v2/memberSave")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest cMember) {
		
		System.out.println("JpaRestApiController /restApi/v2/memberSave cMember.getName() : " + cMember.getName());
		
		log.info("cMember.getName() : {}.", cMember.getName());
		log.info("cMember.getSal() : {}.", cMember.getSal());
		
		Member member = new Member();
		
		member.setName(cMember.getName());
		member.setSal(cMember.getSal());
		
		Long id = memberService.saveMember(member);
		
		return new CreateMemberResponse(id);
		
	}

	// buffer class
	@Data
	static class CreateMemberRequest {
		
		@NotEmpty
		private String name;
		
		private Long 	sal;
		
	}
	
	@Data		// @Data가 @RequiredArgsConstructor를 포괄하고 있어서 클래스 내부에 기본생성자를 자동으로 만들어줌
	static class CreateMemberResponse {	// class JpaRestApiController 내의 내장 class
		
		private final Long id;
		
	}

	// Bad API
	@GetMapping("/restApi/v1/members")
	public List<Member> membersVer1() {
		
		System.out.println("JpaRestApiController /restApi/v1/members Start!");
		
		List<Member> listMember = memberService.getListAllMember();
		
		return listMember;
		
	}
	
}