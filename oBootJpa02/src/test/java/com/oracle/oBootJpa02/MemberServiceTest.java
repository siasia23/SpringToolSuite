package com.oracle.oBootJpa02;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.repository.MemberRepository;
import com.oracle.oBootJpa02.service.MemberService;

// @SpringBootTest : 스프링 부트 띄우고 테스트 (이게 없으면 @Autowired 다 실패)
// 반복 가능한 테스트 지원, 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고
// 테스트가 끝나면 트랜잭션을 강제로 롤백

@SpringBootTest
@Transactional
public class MemberServiceTest {

	// Service Injection
	@Autowired
	MemberService memberService;
	
	// DAO Injection
	@Autowired
	MemberRepository memberRepository;
	
	@BeforeEach
	public void before1() {
		System.out.println("Test @BeforeEach ...");
	}
	
	@Test
	@Rollback(value = false)		// rollback false = commit
	// 회원 등록
	public void memberSave() {
		
		// 1. 테스트 조건
		Member member = new Member();
		member.setTeamname("테스트팀");
		member.setName("테스트");
		
		// 2. 테스트할 메소드
		Member member3 = memberService.join(member);
		
		// 3. 결과
		System.out.println("MemberServiceTest memberSave() member.getId() : " + member.getId());
		System.out.println("MemberServiceTest memberSave() member3.getId() : " + member3.getId());
		
	}
	
	@Test
	// 회원 조회
	public void memberFind() {
		
		Long findId = 1L;
		
		List<Member> memberList =  memberService.getListAllMember();
		System.out.println("MemberServiceTest memberFind() memberList.size() : " + memberList.size());

		Optional<Member> member = memberService.findByMember(findId);
		System.out.println("MemberServiceTest memberFind() member : " + member);
		
	}
	
}