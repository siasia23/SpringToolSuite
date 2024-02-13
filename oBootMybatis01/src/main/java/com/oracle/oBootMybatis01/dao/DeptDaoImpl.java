package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Dept;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeptDaoImpl implements DeptDao {

	private final SqlSession session;
	
	@Override
	public List<Dept> deptSelect() {

		List<Dept> deptList = null;
		
		deptList = session.selectList("siaSelectDept");
		
		return deptList;
	}

}
