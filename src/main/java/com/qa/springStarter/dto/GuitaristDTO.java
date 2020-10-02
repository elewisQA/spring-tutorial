package com.qa.springStarter.dto;

import com.qa.springStarter.persistence.domain.Band;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GuitaristDTO {

	private Long id;
	private String name;
	private int noOfStrings;
	private String type;
	
}
