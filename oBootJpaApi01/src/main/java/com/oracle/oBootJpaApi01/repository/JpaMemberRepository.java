package com.oracle.oBootJpaApi01.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.oBootJpaApi01.domain.Member;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Repository

@RequiredArgsConstructor	// : Generates a constructor with required arguments.
										// Required arguments are final fields and fields with constraints such as @NonNull. 

public class JpaMemberRepository implements MemberRepository {

	private final EntityManager em;
	
	@Override
	public Long save(@Valid Member member) {

		System.out.println("JpaMemberRepository save() Before!");
		
		em.persist(member);
		
		return member.getId();
		
	}
	
	@Override
	public List<Member> findAll() {

		String query = "select m from Member m";
		
		List<Member> memberList = em.createQuery(query, Member.class).getResultList();
		
		System.out.println("JpaMemberRepository findAll() memberList.size() : " + memberList.size());
		
		return memberList;
		
	}

	@Override
	public void updateByMember(Member member) {

		Member member3 = em.find(Member.class, member.getId());
		
		if (member3 != null) {
			
			// 멤버 저장
			member3.setName(member.getName());
			member3.setSal(member.getSal());

			System.out.println("JpaMemberRepository updateByMember() Updated!");
			
		} else System.out.println("JpaMemberRepository updateByMember() Failed...");
		
	}

	@Override
	public Member findByMember(Long memberId) {

		Member member = em.find(Member.class, memberId);
		
		return member;
		
	}

}
