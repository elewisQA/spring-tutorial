package rest;

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

import com.qa.springStarter.dto.GuitaristDTO;
import com.qa.springStarter.service.GuitaristService;

@RestController
@RequestMapping("/guitarist")
public class GuitaristController {
	
	// Re
	// S
	// T

	private GuitaristService service;
	
	@Autowired
	// So if wiring up fails, object is never created
	public GuitaristController(GuitaristService service) {
		super();
		this.service = service;
	}
	
	
	// create
	@PostMapping("/create") // ResponseEntity is specifically in JSON format 
	public ResponseEntity<GuitaristDTO> create(@RequestBody GuitaristDTO guitarist) {
		GuitaristDTO created = this.service.create(guitarist);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	// readAll
	@GetMapping("/read") 
	public ResponseEntity<List<GuitaristDTO>> getAll() {
		return ResponseEntity.ok(this.service.readAll());
	}
	
	// read
	@GetMapping("/read/{id}")
	public ResponseEntity<GuitaristDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.read(id));
	}
	
	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<GuitaristDTO> update(@PathVariable Long id, @RequestBody GuitaristDTO guitarist) {
		GuitaristDTO updated = this.service.update(guitarist, id);
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	
	// delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<GuitaristDTO> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
}
