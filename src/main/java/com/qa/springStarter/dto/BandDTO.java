package com.qa.springStarter.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;

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
//@ElementCollection(fetch = FetchType.LAZY)
public class BandDTO {
	
	private Long id;
	private String bandName;
	private List<GuitaristDTO> guitarists = new ArrayList<>();
	
}
