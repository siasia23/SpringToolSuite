package com.oracle.oBootMybatis01.model;

import lombok.Data;

// table join

@Data
public class EmpDept {

	// EMP table
	private int 		empno;
	private String 	ename;
	private String 	job;
	private int 		mgr;
	private String 	hiredate;
	private int 		sal;
	private int 		comm;
	private int 		deptno;
	
	// DEPT table
	private String dname;
	private String loc;
	
}
