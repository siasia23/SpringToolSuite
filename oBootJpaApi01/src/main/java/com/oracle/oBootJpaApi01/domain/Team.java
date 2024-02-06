package com.oracle.oBootJpaApi01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity

// @Data = @Getter + @Setter + @ToString
@Data

@SequenceGenerator(	name = "team_seq_gen5",
								sequenceName = "team_seq_generator5",
								initialValue = 1,
								allocationSize = 1)

@Table(name = "team5")
public class Team {

	@Id
	@GeneratedValue(	strategy = GenerationType.SEQUENCE,
								generator = "team_seq_gen5")
	private Long team_id;
	
	@Column(name = "teamname")
	private String name;
	
}
