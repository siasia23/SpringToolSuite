package com.oracle.oBootJpa02.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity		// 논리적 table (객체)

// @Data = @Getter + @Setter + @ToString
@Getter
@Setter
@ToString

// @SequenceGenerator : sequence 생성
@SequenceGenerator(name = "member_seq_gen", 						// name : Java 객체 name
								sequenceName = "member_seq_generate", 	// sequenceName : DB sequence name
								initialValue = 1,
								allocationSize = 1)

@Table(name = "member2")		// 물리적 table (DB)
public class Member {

	// primary key
	@Id		

	// @GeneratedValue : Provides for the specification of generation strategies for the values of primary keys. 
	@GeneratedValue(	strategy = GenerationType.SEQUENCE,
								generator = "member_seq_gen")					// generator = Java 객체 name
							// strategy = GenerationType.AUTO 로 대체 가능함? 아마??
	
	@Column(name = "member_id", precision = 10)		// @Column : DB column 정보 설정
	private Long 		id;
	
	@Column(name = "user_name", length = 50)
	private String 	name;
	
	private Long 		sal;
	
	// 테이블 간의 관계 설정 (table join)
	@ManyToOne		// = 다 : 1
	
	// foreign key
	@JoinColumn(name = "team_id")		// name = DB physical column name
	private Team 		team;
	
	// @Transient : Specifies that the property or field is not persistent.
		// buffer로 가지고는 다니지만 column으로 관리하고 싶진 않다.
		// 즉!!! EntityManager가 관리하지 않는 Entity이다.
		// 즉!!!!!!! persistence context와 관계가 없다.
		// 즉!!!!!!!!!!!!!!!!! 객체인건 맞는데, DB와 연결되지 않는 객체다!!!!
	
	@Transient
	private String 	teamname;
	
	@Transient
	private Long 		teamid;
	
}
