package com.oracle.oBootMybatis01.model;

import java.util.Date;

import lombok.Data;

@Data
public class Member1 {

	private String id;
	private String name;
	private String password;
	private Date reg_date;
	
}
