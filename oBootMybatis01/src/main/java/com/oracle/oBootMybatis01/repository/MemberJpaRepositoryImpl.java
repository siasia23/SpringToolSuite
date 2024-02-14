package com.oracle.oBootMybatis01.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryImpl implements MemberJpaRepository {

	private final EntityManager em;
	
	@Override
	public Member save(Member member) {

		System.out.println("MemberJpaRepositoryImpl save() Start!");
		
		em.persist(member);
		
		return member;
	}

	@Override
	public List<Member> findAll() {

		System.out.println("MemberJpaRepositoryImpl findAll() Start!");
		
		String query = "select m from Member m";
		
		List<Member> memberList = em.createQuery(query, Member.class).getResultList();
		
		return memberList;
	}

	@Override
	public Optional<Member> findById(Long memberId) {

		Member member = em.find(Member.class, memberId);
		
		return Optional.ofNullable(member);
	}

	@Override
	public void updateByMember(Member member) {

		System.out.println("merge 전 멤버 : " + member);
		
		// merge : given entity 만 업뎃해버림. 값 안 주면 null 로 업뎃 돼버림. 지양하자!!!!
//		em.merge(member);
		
		Member member3 = em.find(Member.class, member.getId());
		
		member3.setId(member.getId());
		member3.setName(member.getName());
		
	}

}
