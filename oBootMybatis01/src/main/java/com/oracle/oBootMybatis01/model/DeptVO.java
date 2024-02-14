package com.oracle.oBootMybatis01.model;

import lombok.Data;

// DTO가 instance 개념이라면, VO는 literal 개념

// DTO는 가변성을 지닌 class (데이터 전송을 위해 존재)
// VO는 값 그 자체의 의미를 가진 불변 class (read-only)

// VO = Value Object

@Data
public class DeptVO {

	// 입력 (IN)
	private int deptno;
	private String dname;
	private String loc;
	
	// 출력 (OUT)
	private int odeptno;
	private String odname;
	private String oloc;
	
}
