package com.qa.springStarter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.springStarter.dto.GuitaristDTO;
import com.qa.springStarter.persistence.domain.Guitarist;
import com.qa.springStarter.service.GuitaristService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

@SpringBootTest
public class GuitaristControllerUnitTest {
	
	@Autowired
	private GuitaristController controller;
	// Autowire the object / class being tested
	
	@MockBean
	private GuitaristService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private GuitaristDTO mapToDTO(Guitarist guitarist) {
		return this.modelMapper.map(guitarist, GuitaristDTO.class);
	}
	
	// Testing variables
	private List<Guitarist> guitaristList;
	private Guitarist tester;
	private Guitarist testerId;
	private GuitaristDTO dto;
	
	// Constants
	private final Long id = 1L;
	private final String name = "Syd";
	private final Integer stringCount = 4;
	private final String type = "Bass";
	
	@BeforeEach
	void init() {
		this.guitaristList = new ArrayList<>();
		this.tester = new Guitarist(this.name, this.stringCount, this.type);
		this.testerId = new Guitarist(
				tester.getName(),
				tester.getNoOfStrings(), 
				tester.getType());
		this.testerId.setId(id);
		this.guitaristList.add(testerId);
		this.dto =  this.mapToDTO(testerId);
	}
	
	@Test
	void createTest() {
		// Test setup
		when(this.service.create(tester))
		.thenReturn(this.dto);
		
		// Test Assertion
		GuitaristDTO testCreated = this.dto;
		assertThat(new ResponseEntity<GuitaristDTO>(testCreated, HttpStatus.CREATED))
		.isEqualTo(this.controller.create(tester));
		
		// Check appropriate methods were called
		verify(this.service, times(1)).create(this.tester);
	}
	
	@Test
	void readTest() {
		when(this.service.read(this.id))
		.thenReturn(this.dto);
		
		GuitaristDTO testRead = this.dto;
		assertThat(new ResponseEntity<GuitaristDTO>(
				testRead, HttpStatus.OK))
		.isEqualTo(this.controller.getById(this.id));
	}
	
	@Test
	void readAllTest() {
		when(this.service.readAll())
		.thenReturn(this.guitaristList.stream()
				.map(this::mapToDTO).collect(Collectors.toList()));
		
		// Assert whether the returned list is empty or not
		assertThat(this.controller.getAll().getBody().isEmpty())
		.isFalse();
		
		verify(this.service, times(1)).readAll();
	}
	
	@Test
	void updateTest() {
		Guitarist newGuitarist = new Guitarist("Syd", 4, "Bass");
		GuitaristDTO newDTOId = new GuitaristDTO(this.id, "Syd", 4, "Bass");
		
		// feed to mock
		when(this.service.update(newGuitarist, this.id))
		.thenReturn(newDTOId);
		
		// Test assertion - returned object matches expected
		assertThat(new ResponseEntity<GuitaristDTO>(newDTOId, HttpStatus.ACCEPTED))
		.isEqualTo(this.controller.update(this.id, newGuitarist));
		
		// Check service called
		verify(this.service, times(1)).update(newGuitarist, this.id);
	}
	
	@Test
	void deleteTest() {
		this.controller.delete(this.id);
		
		// Verify service called
		verify(this.service, times(1)).delete(this.id);
	}
}
