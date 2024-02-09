package com.oracle.oBootJpa02.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.repository.MemberRepository;

@Transactional
// JPA는 Service 계층에 transaction 추가 (data 무결성 유지 위해)

// Spring은 해당 service class의 method 실행 시 transaction 시작
// method 정상 종료되면 해당 transaction Commit(= Query 날림) (단, Runtime Exception 발생하면 Rollback)

// JPA 통한 모든 데이터 변경은 transaction 내에서 실행됨

public class MemberService {

	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	// 회원가입
	public Member join(Member member) {
		
		System.out.println("MemberService join() member.getName() : " + member.getName());
		
		memberRepository.save(member);
		
		return member;
		
	}
	
	// 전체회원 조회
	public List<Member> getListAllMember() {
		
		List<Member> listMember = memberRepository.findAll();
		
		System.out.println("MemberService getListAllMember() listMember.size() : " + listMember.size());
		
		return listMember;
	
	}

	// 회원검색
	public List<Member> getListSearchMember(String searchName) {

		System.out.println("MemberService getListSearchMember Start!");
		
		System.out.println("MemberService getListSearchMember searchName : " + searchName);
		
		List<Member> listMember = memberRepository.findByNames(searchName);
		System.out.println("MemberService getListSearchMember listMember.size() : " + listMember.size());
		
		return listMember;
	}

	public Optional<Member> findByMember(Long id) {

		Optional<Member> member = memberRepository.findByMember(id);
		
		System.out.println("MemberService findByMember() member : " + member);
		
		return member;
	}

	public void memberUpdate(Member member) {

		System.out.println("MemberService memberUpdate member : " + member);
		
		memberRepository.updateByMember(member);
		
	}
	
}
