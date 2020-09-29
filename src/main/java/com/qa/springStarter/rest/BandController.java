package com.qa.springStarter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springStarter.dto.BandDTO;
import com.qa.springStarter.service.BandService;

@RestController
@RequestMapping("/band")
public class BandController {

	private BandService service;
	
	@Autowired
	public BandController(BandService service) {
		super();
		this.service = service;
	}
	
	// Create
	@PostMapping("/create")
	public ResponseEntity<BandDTO> create(@RequestBody BandDTO band) {
		BandDTO created = this.service.create(band);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	// readAll
	@GetMapping("/readAll")
	public ResponseEntity<List<BandDTO>> getAll() {
		return ResponseEntity.ok(this.service.readAll());
	}
	
	// read
	@GetMapping("/read/{id}")
	public ResponseEntity<BandDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.read(id));
	}
	
	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<BandDTO> update(@PathVariable Long id, @RequestBody BandDTO band) {
		BandDTO updated = this.service.update(band, id);
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	
	// delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BandDTO> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
}

