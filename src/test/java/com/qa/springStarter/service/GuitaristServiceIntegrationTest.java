package com.qa.springStarter.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.springStarter.dto.GuitaristDTO;
import com.qa.springStarter.persistence.domain.Guitarist;
import com.qa.springStarter.persistence.repository.GuitaristRepository;

@SpringBootTest
public class GuitaristServiceIntegrationTest {

	// Setup & init service and repo
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private GuitaristService service;
	
	@Autowired
	private GuitaristRepository repo;
	
	private Guitarist tester;
	private Guitarist testerId;
	
	// Define Mapping function
	private GuitaristDTO mapToDTO(Guitarist guitarist) {
		return this.modelMapper.map(guitarist, GuitaristDTO.class);
	}
	
	// Testing constants
	final Long id = 1L;
	final String exampleName = "Syd";
	final Integer exampleStringCount = 4;
	final String exampleType = "Bass";
	
	// Initialise variables for all tests
	@BeforeEach
	void init() {
		this.repo.deleteAll(); // Clear all data
		this.tester = new Guitarist(
				this.exampleName,
				this.exampleStringCount,
				this.exampleType);
		this.testerId = this.repo.save(this.tester);
	}
	
	@Test
	void testCreate() {
		// Test assertion
		assertThat(this.mapToDTO(this.testerId))
		.isEqualTo(this.service.create(tester));
	}
	
	@Test
	void testRead() {
		assertThat(this.service.read(testerId.getId()))
		.isEqualTo(this.mapToDTO(this.testerId));
	}
	
	@Test
	void testReadAll() {
		// Since readAll returns a list, need to put test data into list
		// done through same method (collecting a stream)
		assertThat(this.service.readAll())
		.isEqualTo(Stream.of(
				this.mapToDTO(testerId))
				.collect(Collectors.toList())
				);
	}

	@Test
	void testUpdate() {
		// Setup example updated-data
		Guitarist newGuitarist = new Guitarist("Sydney", 5, "Electric");
		GuitaristDTO updatedDTO = new GuitaristDTO(
				this.testerId.getId()+1, // Unsure where increment comes from
				newGuitarist.getName(),
				newGuitarist.getNoOfStrings(), 
				newGuitarist.getType());
		
		// Test Assertion
		assertThat(this.service.update(newGuitarist, this.testerId.getId()))
		.isEqualTo(updatedDTO);
	}
	
	@Test
	void testDelete() {
		assertThat(this.service.delete(this.testerId.getId()))
		.isTrue();
	}
}
