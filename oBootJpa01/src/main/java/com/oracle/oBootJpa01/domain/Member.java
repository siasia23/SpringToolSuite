 package com.oracle.oBootJpa01.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// @Entity : JPA가 DB 관리하기 위해 필요함 (MyBatis에서의 명칭은 DTO)
	// 객체모델링, 논리모델링에서는 Entity. 물리모델링에서는 Table. 명칭 차이!!!!!!!

@Entity

// ORM (Object Relational Mapping) 
	// Object = 논리적 객체. 여기서는 Class명 'Member'
	// Relational = 물리적 객체. 여기서는 DB의 Table name 'member1'

@Table(name = "member1")		// 물리적 객체명 : member1

// Lombok getter, setter
@Getter
@Setter

// Lombok toString
@ToString

public class Member {				// 논리적 객체명 : Member
	
	// @Id : DB Primary Key
	@Id
	private Long id;
	
	private String name;

/*
	@Override
	public String toString() {
		
		String returnStr = "";
		returnStr = "[id :" + this.id + ", name : " + this.name + "]";
		
		return returnStr;
		
	}
*/	
}
