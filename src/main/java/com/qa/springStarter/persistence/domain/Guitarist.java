package com.qa.springStarter.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Guitarist {
	
	@Id
	@GeneratedValue // This does auto-increment for us
	private Long id;

	@Column
	@Size(min = 1, max = 100)
	private String type;
	
	@Column(name="name")
	@NotNull
	@Size(min = 1, max = 100)
	private String name;
	
	@Column(name="string_count")
	private int noOfStrings;

	//@ManyToOne(targetEntity = Band.class)
	@ManyToOne
	private Band band;
	
	public Guitarist(String name, Integer noOfStrings, String type) {
		super();
		this.name = name;
		this.noOfStrings = noOfStrings;
		this.type = type;
	}
}
