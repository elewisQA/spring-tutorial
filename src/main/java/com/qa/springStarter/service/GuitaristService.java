package com.qa.springStarter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.springStarter.dto.GuitaristDTO;
import com.qa.springStarter.exception.GuitaristNotFoundException;
import com.qa.springStarter.persistence.domain.Guitarist;
import com.qa.springStarter.persistence.repository.GuitaristRepository;


@Service
public class GuitaristService {
	
	private GuitaristRepository repo;
	private ModelMapper mapper;
	
	@Autowired
	public GuitaristService(GuitaristRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private GuitaristDTO mapToDTO(Guitarist guitarist) {
		return this.mapper.map(guitarist, GuitaristDTO.class);
	}
	
	private Guitarist mapFromDTO(GuitaristDTO guitaristDTO) {
		return this.mapper.map(guitaristDTO, Guitarist.class);
	}
	
	// Create
	public GuitaristDTO create(Guitarist guitarist) {
		//Guitarist toSave = this.mapFromDTO(guitaristDTO);
		Guitarist saved = this.repo.save(guitarist);
		return this.mapToDTO(saved);
	}
	
	// Read All
	public List<GuitaristDTO> readAll() {
		List<GuitaristDTO> myList = this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		return myList;
	}
	
	// Read by ID
	public GuitaristDTO read(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(GuitaristNotFoundException::new));
	}
	
	// Update
	public GuitaristDTO update(Guitarist guitarist, Long id) {
		// Requires both since update uses put - a combintaion of create and delete
		Guitarist toUpdate = this.repo.findById(id).orElseThrow(GuitaristNotFoundException::new);
		toUpdate.setId(guitarist.getId());
		toUpdate.setName(guitarist.getName());
		toUpdate.setNoOfStrings(guitarist.getNoOfStrings());
		toUpdate.setType(guitarist.getType());
		return this.mapToDTO(this.repo.save(guitarist));
	}
	
	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id); // Delete, then confirm (NOT) if exists
	}
}
