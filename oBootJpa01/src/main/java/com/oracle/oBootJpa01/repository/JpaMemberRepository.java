package com.oracle.oBootJpa01.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.oBootJpa01.domain.Member;

import jakarta.persistence.EntityManager;

@Repository
public class JpaMemberRepository implements MemberRepository {

	// JPA DML 작업 할 때 EntityManager 필수!!!!!!!!!!!
	private final EntityManager em;
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}
	
	// 회원가입
	// createMemberForm.html
	@Override
	public Member memberSave(Member member) {

		// persist() : Make an instance managed and persistent.
		// 이 메소드 호출하기 전의 default = transient
		
		// persistence context에 담겨있지만 아직 transient 상태인 member라는 instance를 
		// persistent하게 만들어서 
		// EntityManager가 관리하게 하자.
		em.persist(member);
		
		System.out.println("JpaMemberRepository memberSave member After...");
		
		return member;
		
	}

	// 회원 목록 조회
	// memberList.html
	@Override
	public List<Member> findAllMember() {

		// JPA 문법.
		// Entity 'Member' 객체(class)를 alias 'm'으로 정의해서 쿼리에 넣겟다.
		String query = "select m from Member m";
		
		// query의 m = Member.class의 return value
		// getResultList() : Execute a SELECT query and return the query results as a typed List.
		List<Member> memberList = em.createQuery(query, Member.class).getResultList();
		
		System.out.println("JpaMemberRepository findAllMember memberList.size() : " + memberList.size());
		
		return memberList;
		
	}

	// 회원 검색
	// memberList.html
	@Override
	public List<Member> findByNames(String searchName) {

		// DB의 LIKE 문장에 쓰이는 %
		String pname = searchName + "%";
		
		System.out.println("JpaMemberRepository findByNames pname : " + pname);
		
		String query = "select m from Member m where name Like :name";
		
		List<Member> memberList = em.createQuery(query, Member.class)
													.setParameter("name", pname)		// pname으로 받아온 값을 query 안의 name에 넣겠다.
													.getResultList();

		System.out.println("JpaMemberRepository memberList.size() : " + memberList.size());
		
		return memberList;
		
	}

}