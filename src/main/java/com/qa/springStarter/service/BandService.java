package com.qa.springStarter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.springStarter.dto.BandDTO;
import com.qa.springStarter.exception.BandNotFoundException;
import com.qa.springStarter.persistence.domain.Band;
import com.qa.springStarter.persistence.repository.BandRepository;

@Service
public class BandService {
	
	private BandRepository repo;
	private ModelMapper mapper;
	
	@Autowired
	public BandService(BandRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private BandDTO mapToDTO(Band band) {
		return this.mapper.map(band, BandDTO.class);
	}
	
	private Band mapFromDTO(BandDTO bandDTO) {
		return this.mapper.map(bandDTO, Band.class);
	}
	
	// Create
	public BandDTO create(BandDTO bandDTO) {
		Band toSave = this.mapFromDTO(bandDTO);
		Band saved = this.repo.save(toSave);
		return this.mapToDTO(saved);
	}
	
	// Read All
	public List<BandDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Read by ID
	public BandDTO read(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(BandNotFoundException::new));
	}
	
	// Update
	public BandDTO update(BandDTO bandDTO, Long id) {
		Band toUpdate = this.repo.findById(id).orElseThrow(BandNotFoundException::new);
		toUpdate.setName(bandDTO.getBandName());
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}
