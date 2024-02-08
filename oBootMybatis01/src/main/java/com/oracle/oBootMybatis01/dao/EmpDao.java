package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.Emp;

public interface EmpDao {

	int 			totalEmp() ;

	List<Emp> listEmp(Emp emp);

	Emp 			detailEmp(int empno);

	int 			updateEmp(Emp emp);
	
}
