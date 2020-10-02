package com.qa.springStarter.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Band {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "band_name", unique = true)
	private String name;
	
	//@OneToMany(targetEntity = Guitarist.class, cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "band")
	private List<Guitarist> guitarists = new ArrayList<>();

	public Band(String name) {
		this.name = name;
		this.guitarists = new ArrayList<>();
	}
}
