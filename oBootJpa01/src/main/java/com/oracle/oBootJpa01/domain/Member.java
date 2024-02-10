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

// ORM (Object Relational Mapping) : 객체와 DB를 연결시켜주는 '도구'!!
	// Object = 논리적 객체. 여기서는 Class name 'Member'
	// Relational = 물리적 객체. 여기서는 DB의 Table name 'member1'

// JPA (Java Persistence API) : Java에서 사용되는 ORM의 표준!
	// 인터페이스니까 실제로 사용하기 위해 프레임워크 필요함.
	// 그 프레임워크가 Hibernate
	// 즉!!! Hibernate는 JPA의 구현체!!!

	// Hibernate는 내부적으로 JDBC API 사용함

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
