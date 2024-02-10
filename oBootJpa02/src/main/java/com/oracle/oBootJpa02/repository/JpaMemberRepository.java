package com.oracle.oBootJpa02.repository;

import java.util.List;
import java.util.Optional;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.domain.Team;

import jakarta.persistence.EntityManager;

// DAO 구현체
public class JpaMemberRepository implements MemberRepository {

	// persistence context 관리자
	private final EntityManager em;
	
	// DAO 객체
	public JpaMemberRepository(EntityManager em) {

		// DAO (DML Logic) 는 persistence context 를 통해서 수행하겠다.
		this.em = em;
		
	}
	
	@Override
	public Member save(Member member) {

		Team team = new Team();

		// Team 저장(persist()) (Member-Team 간의 foreign key 설정)
		team.setName(member.getTeamname());
		em.persist(team);
		
		member.setTeam(team);
		em.persist(member);
		
		// find(Entity class, primary key) : Find by primary key.
		Member member3 = em.find(Member.class, member.getId());
		
		// member를 찍어보자
		System.out.println("JpaMemberRepository save() member : " + member);
		
		return member3;
	}

	@Override
	public List<Member> findAll() {

		String query = "select m from Member m";
		
		List<Member> memberList = em.createQuery(query, Member.class).getResultList();
		
		return memberList;
	}

	@Override
	public List<Member> findByNames(String searchName) {

		String pname = searchName + "%";
		System.out.println("JpaMemberRepository findByNames pname : " + pname);
		
		String query = "select m from Member m where name Like :name";
		
		List<Member> memberList = em.createQuery(query, Member.class)
													.setParameter("name", pname)
													.getResultList();
		
//		- error! -
/*		String query = "select m from Member m where m.name Like : searchName%";
		
		List<Member> memberList = em.createQuery(query, Member.class)
													.setParameter("searchName", searchName)
													.getResultList();
*/		
		System.out.println("JpaMemberRepository findByNames() memberList.size() : " + memberList.size());
		
		return memberList;
	}

	@Override
	public Optional<Member> findByMember(Long id) {

		Member member = em.find(Member.class, id);
		
		// Optional : null값을 포함해서 값을 가져옴. > NullPointerException 회피 가능
		return Optional.ofNullable(member);
	}

	@Override
	public void updateByMember(Member member) {

		Member member3 = em.find(Member.class, member.getId());
		
		if (member3 != null) {
			
			Team team = em.find(Team.class, member.getTeamid());
			
			if (team != null) {
				
				// Team persist
				team.setName(member.getTeamname());
				em.persist(team);
				
			}
			
			// Member persist
			member3.setTeam(team);
			member3.setName(member.getName());
			em.persist(member3);
			
			System.out.println("JpaMemberRepository updateByMember() member : " + member);
			
		} else {
			
			System.out.println("JpaMemberRepository updateByMember에 member가 존재하지 않음");
			
		}
		
	}

}
