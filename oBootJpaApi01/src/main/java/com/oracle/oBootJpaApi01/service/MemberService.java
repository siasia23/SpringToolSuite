package com.oracle.oBootJpaApi01.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.repository.MemberRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor

@Transactional		// 서비스 단위의 무결성 유지를 위해

public class MemberService {

	// service에 필수인 DAO!!!!
	private final MemberRepository memberRepository;

	// 멤버 저장 (insert)
	public Long saveMember(@Valid Member member) {

		System.out.println("MemberService saveMember() member.getName() : " + member.getName());
		
		Long id = memberRepository.save(member);
		
		return id;
	}
	
	// 전체회원 조회 API (select)
	public List<Member> getListAllMember() {

		List<Member> listMember = memberRepository.findAll();
		
		System.out.println("MemberService getListAllMember() listMember.size() : " + listMember.size());
		
		return listMember;
	}

	public void updateMember(Long id, String name, Long sal) {

		Member member = new Member();
		
		member.setId(id);
		member.setName(name);
		member.setSal(sal);
		
		System.out.println("MemberService updateMember() member.getName() : " + member.getName());
		System.out.println("MemberService updateMember() member.getSal() : " + member.getSal());
		
		memberRepository.updateByMember(member);
		
	}

	public Member findByMember(Long memberId) {

		Member member = memberRepository.findByMember(memberId);
		
		System.out.println("MemberService findByMember() member.getId() : " + member.getId());
		System.out.println("MemberService findByMember() member.getName() : " + member.getName());
		
		return member;
	}
	
}
