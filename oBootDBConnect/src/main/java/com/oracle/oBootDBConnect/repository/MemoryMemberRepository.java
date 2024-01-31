package com.oracle.oBootDBConnect.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oracle.oBootDBConnect.domain.Member1;

@Repository
public class MemoryMemberRepository implements MemberRepository {

	private static Map<Long, Member1> store = new HashMap<>();
	private static long sequence = 0L;
	
	@Override
	public Member1 save(Member1 member1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member1> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
