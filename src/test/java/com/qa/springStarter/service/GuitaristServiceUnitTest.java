package com.qa.springStarter.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class GuitaristServiceUnitTest {

	// Autowire the service, i.e: set it an instance for testing
	@Autowired
	private GuitaristService service;
	
	// Testing Beans
	// These are beans that have to be mocked for testing to take place
	@MockBean
	private GuitaristRepository repo;
	
	@MockBean
	private ModelMapper modelMapper;
	
	// Testing Variables
	private List<Guitarist> guitaristList;
	private Guitarist tester;
	private Guitarist testerId;
	private Guitarist emptyTester;
	private GuitaristDTO dto;
	
	// Testing constants
	final Long id = 1L;
	final String exampleName = "Syd";
	final Integer exampleStringCount = 4;
	final String exampleType = "Bass";
	
	// Define Mapping function
	private GuitaristDTO mapToDTO(Guitarist guitarist) {
		return this.modelMapper.map(guitarist, GuitaristDTO.class);
	}
	
	// Initialise variables before each test
	@BeforeEach
	void init() {
		// Instantiate list to emulate returned data
		this.guitaristList = new ArrayList<>();
		this.guitaristList.add(tester);
		
		// Create example guitarist
		this.tester = new Guitarist(
				this.exampleName, 
				this.exampleStringCount, 
				this.exampleType);
		
		// Create example guitarist with an Id
		this.testerId = new Guitarist(
				tester.getName(), 
				tester.getNoOfStrings(), 
				tester.getType());
		this.testerId.setId(this.id);
		
		// Create example empty-guitarist
		this.emptyTester = new Guitarist();
		
		// Create DTO from guitarist with Id
		this.dto = new ModelMapper().map(testerId, GuitaristDTO.class);
	}
	
	@Test
	void createTest() {
		// Set fake data to be returned
		when(this.modelMapper.map(mapToDTO(tester), Guitarist.class))
		.thenReturn(tester);
		when(this.repo.save(tester))
		.thenReturn(testerId);
		when(this.modelMapper.map(testerId, GuitaristDTO.class))
		.thenReturn(dto);
		
		// Test assertion
		assertThat(this.dto)
		.isEqualTo(this.service.create(tester));
		
		// Check 'repo' was used in this test
		verify(this.repo, times(1)).save(this.tester);
	}
	
	@Test
	void readTest() {
		// Set fake data to be returned
		when(repo.findById(this.id))
		.thenReturn(Optional.of(this.testerId));
		when(this.modelMapper.map(testerId, GuitaristDTO.class))
		.thenReturn(dto);
		
		// Test assertions
		assertThat(this.dto).isEqualTo(this.service.read(this.id));
		
		// Check that 'repo' was used in this test
		verify(this.repo, times(1)).findById(this.id);
		
	}
	
	@Test
	void readAllTest() {
		// Setup fake data
		when(repo.findAll())
		.thenReturn(this.guitaristList);
		when(this.modelMapper.map(testerId, GuitaristDTO.class))
		.thenReturn(dto);
		
		// Test assertions
		assertThat(this.service.readAll()
				.isEmpty())
				.isFalse();
		
		// Check repo was used in test
		verify(repo, times(1)).findAll();
	}
	
	@Test
	void updateTest() {
		// Example data for the object returned by the update query 
		GuitaristDTO newDTO = new GuitaristDTO(null, "Sydney", 5, "Electric");
		Guitarist newGuitarist = new Guitarist("Sydney", 5, "Electric");
		
		// Match "updated" guitarist & DTO to global test ones
		final Long ID = 1L; // Permanent ID
		Guitarist updatedGuitarist = new Guitarist(
				newGuitarist.getName(),
				newGuitarist.getNoOfStrings(),
				newGuitarist.getType());
		GuitaristDTO updatedDTO = new GuitaristDTO(ID,
				updatedGuitarist.getName(),
				updatedGuitarist.getNoOfStrings(),
				updatedGuitarist.getType());
		
		// Setup fake-data / mock data
		when(this.repo.findById(this.id))
		.thenReturn(Optional.of(newGuitarist));
		
		when(this.repo.save(updatedGuitarist))
		.thenReturn(updatedGuitarist);
		
		when(this.modelMapper.map(updatedGuitarist, GuitaristDTO.class))
		.thenReturn(updatedDTO);
		
		// Test Assertions
		assertThat(updatedDTO).isEqualTo(this.service.update(newGuitarist, this.id));
		
		// Check that these methods were used in testing
		verify(this.repo, times(1))
		.findById(ID);
		
		verify(this.repo, times(1))
		.save(updatedGuitarist);
	}
	
	@Test
	void deleteTest() {
		// Setup mock conditions
		when(this.repo.existsById(id))
		.thenReturn(true, false);
		
		// Test assertions
		assertThat(this.service.delete(id))
		.isFalse();

		// Check that these repo methods were called during testing
		verify(this.repo, times(1))
		.deleteById(id);
		
		verify(this.repo, times(1))
		.existsById(id);
	}
}
