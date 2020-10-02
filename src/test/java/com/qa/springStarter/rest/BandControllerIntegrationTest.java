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
import com.qa.springStarter.dto.BandDTO;
import com.qa.springStarter.dto.GuitaristDTO;
import com.qa.springStarter.persistence.domain.Band;
import com.qa.springStarter.persistence.repository.BandRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BandControllerIntegrationTest {

	@Autowired
	private MockMvc mock;
	
	@Autowired
	private BandRepository repo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private BandDTO mapToDTO(Band band) {
		return this.modelMapper.map(band, BandDTO.class);
	}
	
	// Testing Variables
	private Long id;
	private List<GuitaristDTO> guitarists;
	private Band tester;
	private Band testerId;
	private BandDTO dto;
	
	@BeforeEach
	void init() {
		this.repo.deleteAll(); // Ensure is emptied
		this.tester = new Band("Pink Floyd");
		this.testerId = this.repo.save(this.tester);
		this.dto = this.mapToDTO(testerId);
		this.id = this.testerId.getId();
		this.guitarists = new ArrayList<>();
	}
	
	@Test
	void testCreate() throws Exception {
		this.mock.perform(
				request(HttpMethod.POST, "/band/create").contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.tester))
				.accept(MediaType.APPLICATION_JSON))
		// Check returned statuses & objects correct
		.andExpect(status().isCreated())
		.andExpect(content().json(this.objectMapper.writeValueAsString(this.dto)));
	}
	
	@Test
	void testReadAll() throws Exception {
		List<BandDTO> bandList = new ArrayList<>();
		bandList.add(this.dto);
		
		// Setup strings for comparison
		String expected = this.objectMapper.writeValueAsString(bandList);
		String actual = this.mock.perform(
				request(HttpMethod.GET, "/band/readAll").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		// Test assertion
		assertEquals(expected, actual);
	}
	
	@Test
	void testRead() throws Exception {
		this.mock.perform(
				request(HttpMethod.GET, "/band/read/" + this.id)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(this.objectMapper.writeValueAsString(this.dto)
		));
	}
	
	@Test
	void testUpdate()  throws Exception {
		// Initialise list of Guitarists for use
		
		BandDTO newDTO = new BandDTO(this.id, "Pink Floyd", this.guitarists);
		Band updatedBand = new Band(
				newDTO.getBandName());
		updatedBand.setId(this.id);
		
		// Stringify for comparison
		String expected = this.objectMapper.writeValueAsString(
				this.mapToDTO(updatedBand));
		
		String actual = this.mock.perform(
				request(HttpMethod.PUT, "/band/update/" + this.id + 1)
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
				request(HttpMethod.DELETE, "/band/delete/" + this.id));
	}
}
