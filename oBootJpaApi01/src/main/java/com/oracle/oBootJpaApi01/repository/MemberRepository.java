package com.oracle.oBootJpaApi01.repository;

import java.util.List;

import com.oracle.oBootJpaApi01.domain.Member;

import jakarta.validation.Valid;

public interface MemberRepository {

	Long 					save(@Valid Member member);
	
	List<Member> 	findAll();
	
}
