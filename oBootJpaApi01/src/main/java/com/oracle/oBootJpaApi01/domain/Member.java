package com.oracle.oBootJpaApi01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity		// 논리적 table

@Data

// @SequenceGenerator : sequence 생성
@SequenceGenerator(	name = "member_seq_gen5", 						// name : Java instance name
								sequenceName = "member_seq_generate5", 	// sequenceName : DB sequence name
								initialValue = 1,
								allocationSize = 1)

@Table(name = "member5")		// 물리적 table
public class Member {

	@Id		// = primary key
	// @GeneratedValue : Provides for the specification of generation strategies for the values of primary keys. 
	@GeneratedValue(	strategy = GenerationType.SEQUENCE,
								generator = "member_seq_gen5")					// generator = Java instance name
	@Column(name = "member_id")		// @Column : column 정보 설정
	private Long 		id;
	
	@NotEmpty		// : must not be null nor empty.
	@Column(name = "userName")
	private String 	name;
	
	private Long 		sal;
	
	private String		status;
	
	// 테이블 간의 관계 설정 (table join)
	// @ManyToOne : parent table에 할 것!
	@ManyToOne(fetch = FetchType.EAGER)		// = 다 : 1
					// fetch = range, scope, area ...
												// EAGER (default) 	: select 시, joined table 모든 데이터 가져옴
												// LAZY 					: select 시, 해당 테이블의 데이터만 가져옴. 성능면에서 더 좋아서 실무에서 주로 사용됨.
	@JoinColumn(name = "team_id")		// name = DB physical column name
	private Team 		team;
	
	// @Transient : Specifies that the property or field is not persistent.
	@Transient
	// buffer로 가지고는 다니지만 column으로 관리하고 싶진 않다
	private String 	teamname;
	
	@Transient
	private Long 		teamid;
	
}
