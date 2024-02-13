package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.Dept;

public interface DeptDao {

	List<Dept> deptSelect();
	
}
