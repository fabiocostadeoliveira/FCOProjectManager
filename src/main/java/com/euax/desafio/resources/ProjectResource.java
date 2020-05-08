package com.euax.desafio.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.euax.desafio.domain.Project;
import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.ProjectDTO;
import com.euax.desafio.dto.TaskDTO;
import com.euax.desafio.services.ProjectService;



@RestController
@RequestMapping(value = "/projects")
public class ProjectResource {
	
	@Autowired
	private ProjectService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Project> find(@PathVariable Integer id) {
		
		Project project = service.findWithoutValidation(id);
		
		if(project == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		return ResponseEntity.ok().body(project);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ProjectDTO objDto){
		
		Project obj = service.fromDTO(objDto);
		
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(objDto.getId())
				.toUri(); 
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody ProjectDTO objDto, @PathVariable Integer id){
		
		Project project = service.findWithoutValidation(id);
		
		if (project == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		Project updatedObj = service.fromDTO(objDto);

		updatedObj.setId(id);
		
		updatedObj = service.update(project,updatedObj);
		
		return ResponseEntity.noContent().build();
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDTO>> findAll() {
		
		List<Project> list = service.findAll();
		List<ProjectDTO> listDTO = list.stream().map(
				obj -> new ProjectDTO(obj)).collect(Collectors.toList()
		);	
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}

}
