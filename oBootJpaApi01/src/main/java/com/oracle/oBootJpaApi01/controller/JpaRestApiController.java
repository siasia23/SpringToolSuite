package com.oracle.oBootJpaApi01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
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
	
	@Data		// @Data가 @RequiredArgsConstructor를 포괄하고 있고, 필드가 final이어서 클래스 내부에 기본생성자를 자동으로 만들어줌
//	@RequiredArgsConstructor : final field에만 적용됨
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
	
	// Good API ( Easy Version )
		// 목표 : 이름 & 급여 만 전송
	@GetMapping("/restApi/v21/members")
	public Result memberVer21() {
		
		// 모든 데이터
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController /restApi/v21/members findMembers.size() : " + findMembers.size());

		// 모든 데이터 중 내가 필요한 것만 추린 데이터
		List<MemberRtnDto> resultList = new ArrayList<MemberRtnDto>();
		
		// List<Member> findMembers 를 List<MemberRtnDto> resultList 로 이전
		// 이전 목적 : 반드시 필요한 data만 보여줌(외부 노출 최대한 금지)
		for (Member member : findMembers) {
			
			MemberRtnDto memberRtnDto = new MemberRtnDto(member.getName(), member.getSal());
			
			System.out.println("/restApi/v21/members getName() : " + memberRtnDto.getName());
			System.out.println("/restApi/v21/members getSal() : " + memberRtnDto.getSal());
			
			resultList.add(memberRtnDto);
			
		}
		
		System.out.println("/restApi/v21/members resultList.size() : " + resultList.size());
		
//		return new Result(resultList);
//		return resultList;
		return new Result(resultList.size(), resultList);
		
	}
	
	// Good API ( 람다  Version )
		// 목표 : 이름 & 급여 만 전송
	@GetMapping("/restApi/v22/members")
	public Result membersVer22() {
		
		List<Member> findMembers = memberService.getListAllMember();
		
		System.out.println("JpaRestApiController /restApi/v22/members findMembers.size() : " + findMembers.size());
		
		List<MemberRtnDto> memberCollect = findMembers
																// stream() : JAVA 8에서 추가된, 람다를 활용할 수 있는 기술 중 하나
																.stream()
																// map() : 118~127 line의		for (Member member : findMembers)		기능
																.map(m->new MemberRtnDto(m.getName(), m.getSal()))
																// map() 결과로 흩어져있는 데이터를 모아주자(collect 해주자)
																.collect(Collectors.toList());
		
		System.out.println("/restApi/v22/members memberCollect.size() : " + memberCollect.size());
		
		return new Result(memberCollect.size(), memberCollect);
		
	}
	
	// Generic T는 인스턴스를 생성할 때 구체적인 타입으로 변경  --> 유연성
	
	// Generic <---> Specific
	// Generic : class 내부에서 지정하는 것이 아닌, 외부에서 사용자에 의해 지정되는 것.
	//				컴파일 때 해당 타입으로 캐스팅함.
	
	// Generic 장점
		// 1. 타입 불일치를 컴파일 단계에서 방지
		// 2. 타입 체크 및 변환할 필요 없음. 관리하기 편함
		// 3. 비슷한 기능 지원 시 코드 재사용성 향상
	
	@Data
	// generic class 문법 : class ClassName<T> { ... }
	class Result<T> {	// <T> : Type Parameter
								// class 는 기본적으로 parameter 가 존재하지 않지만, 다른 class type 을 parameter 로 받아서 유연하게 구현하기 위해.

		private final int totCout;	// total count
		private final T data;		// T type instance name = data로 하겠다.
		
	}
	
	@Data
	@AllArgsConstructor			// final field 아니어서 필요함. @RequiredArgsConstructor 적용 대상이 아니니까.
	class MemberRtnDto {	// member returned DTO
		
		private String 	name;
		private Long		sal;
		
	}

	/*
	 *   수정 API
	 *
	 *   PUT 방식을사용했는데, PUT은 전체 업데이트를 할 때 사용.
	 *   URI 상에서 '{ }' 로 감싸여있는 부분과 동일한 변수명을 사용하는 방법.
	 *   해당 데이터가 있으면 업데이트를 하기에 
	 *   PUT요청이 여러번 실행되어도 해당 데이터는 같은 상태이기에 멱등(= 실행시켰는데 결과는 똑같음).
	 */
	
	@PutMapping("/restApi/v21/members/{id}")
	public UpdateMemberResponse updateMember21 (
			
			@PathVariable("id") Long id,		// 일반적으로 primary key 이용
			@RequestBody @Valid UpdateMemberRequest uMember
			
			) {
		
		memberService.updateMember(id, uMember.getName(), uMember.getSal());
		
		Member findMember = memberService.findByMember(id);
		
		return new UpdateMemberResponse(findMember.getId(), findMember.getName(), findMember.getSal());
		
	}
	
	@Data
	static class UpdateMemberRequest {
		
		private String 	name;
		private Long 		sal;
		
	}
	
	@Data
	@AllArgsConstructor
	static class UpdateMemberResponse {
		
		private Long		id;
		private String		name;
		private Long		sal;
		
	}
	
}
