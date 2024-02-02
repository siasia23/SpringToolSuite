package com.oracle.oBootJpa01.repository;

import java.util.List;

import com.oracle.oBootJpa01.domain.Member;

// DB를 갑자기 교체할 수 있으므로 DAO쪽은 interface를 필수로 만들어줌.

public interface MemberRepository {

	Member 				memberSave(Member member);
	
	List<Member> 	findAllMember();

	List<Member> 	findByNames(String searchName);
	
}
