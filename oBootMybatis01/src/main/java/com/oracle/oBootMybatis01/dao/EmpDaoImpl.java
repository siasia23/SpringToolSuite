package com.oracle.oBootMybatis01.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {

	// Mybatis DB 연동에 필요한 객체 SqlSession
	private final SqlSession session;
	
	
	@Override
	public int totalEmp() {
		// TODO Auto-generated method stub
		return 0;
	}

}
