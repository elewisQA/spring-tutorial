package com.qa.springStarter.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springStarter.dto.GuitaristDTO;
import com.qa.springStarter.persistence.domain.Guitarist;
import com.qa.springStarter.persistence.repository.GuitaristRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class GuitaristControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private GuitaristRepository repo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private GuitaristDTO mapToDTO(Guitarist guitarist) {
		return this.modelMapper.map(guitarist, GuitaristDTO.class);
	}
	
	// Testing Variables
	private Long id;
	private Guitarist tester;
	private Guitarist testerId;
	private GuitaristDTO dto;
	
	@BeforeEach
	void init() {
		this.repo.deleteAll(); // Empty it before testing
		this.tester = new Guitarist("Syd", 4, "Bass");
		this.testerId = this.repo.save(this.tester);
		this.dto = this.mapToDTO(testerId);
		this.id = this.testerId.getId();
	}
	
	@Test
	void testCreate() throws Exception {
		this.mock.perform(
				request(HttpMethod.POST, "/guitarist/create").contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.tester))
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(content().json(this.objectMapper.writeValueAsString(this.dto)));
	}
	
	@Test
	void testReadAll()  throws Exception {
		List<GuitaristDTO> guitaristList = new ArrayList<>();
		guitaristList.add(this.dto);
		
		// Set up strings to compare values with 
		String expected = this.objectMapper.writeValueAsString(guitaristList);
		String actual = this.mock.perform(
				request(HttpMethod.GET, "/guitarist/readAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testRead() throws Exception {
		this.mock.perform(request(HttpMethod.GET, "/guitarist/read/" + this.id)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(
						this.objectMapper.writeValueAsString(this.dto)
						));
	}
	
	@Test
	void testUpdate() throws Exception {
		// Create objects /w values to test (since using actual, not mock)
		GuitaristDTO newDTO = new GuitaristDTO(this.id, "Syd", 4, "Bass");
		Guitarist updatedGuitarist = new Guitarist(
				newDTO.getName(),
				newDTO.getNoOfStrings(),
				newDTO.getType());
		updatedGuitarist.setId(this.id);
		
		// Stringify for comparison
		String expected = this.objectMapper.writeValueAsString(
				this.mapToDTO(updatedGuitarist));
		
		String actual = this.mock.perform(request(HttpMethod.PUT, "/guitarist/update/" + this.id)
				.contentType(MediaType.APPLICATION_JSON).content(
						this.objectMapper.writeValueAsString(newDTO))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted())
				.andReturn().getResponse().getContentAsString();
		
		// Test assertion
		assertEquals(expected, actual);			
	}
	
	@Test
	void testDelete() throws Exception {
		this.mock.perform(
				request(HttpMethod.DELETE, "/guitarist/delete/" + this.id));
	}
}
