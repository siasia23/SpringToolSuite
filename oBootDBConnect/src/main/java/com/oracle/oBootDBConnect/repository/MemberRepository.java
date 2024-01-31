package com.oracle.oBootDBConnect.repository;

import java.util.List;

import com.oracle.oBootDBConnect.domain.Member1;

public interface MemberRepository {

	// abstract method 선언
	
	Member1 			save(Member1 member1);
	
	List<Member1> 	findAll();
	
}
