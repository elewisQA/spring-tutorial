package com.qa.springStarter.dto;

import java.util.ArrayList;
import java.util.List;

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
public class BandDTO {
	
	private Long id;
	private String bandName;
	private List<GuitaristDTO> guitarists;
	
	public BandDTO(Long id, String bandName) {
		this.id = id;
		this.bandName = bandName;
		guitarists = new ArrayList<>();
	}
}
