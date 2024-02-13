package com.oracle.oBootMybatis01.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Emp {

	private int 		empno;
	@NotEmpty(message = "이름은 필수입력사항")
	private String 	ename;
	private String 	job;
	private int 		mgr;
	private String 	hiredate;
	private int 		sal;
	private int 		comm;
	private int 		deptno;
	
	// 조회용
	private String 	search;
	private String 	keyword;
	private String 	pageNum;
	private int 		start;
	private int 		end;
	
	// page 정보
	private String 	currentPage;
	
}
