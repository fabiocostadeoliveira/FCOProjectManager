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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.TaskDTO;
import com.euax.desafio.services.TaskService;


@RestController
@RequestMapping(value = "/tasks")
public class TaskResouce {
	
	@Autowired
	private TaskService service;
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Task> find(@PathVariable Integer id) {
		
		Task task = service.findWithoutValidation(id);
		
		if (task == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		return ResponseEntity.ok().body(task);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody TaskDTO objDto){
		
		Task obj = service.fromDTO(objDto);
		
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(objDto.getId())
				.toUri(); 
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody TaskDTO objDto, @PathVariable Integer id){
		
		Task task = service.findWithoutValidation(id);
		
		if (task == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		Task updatedObj = service.fromUpdateDTO(objDto);

		updatedObj.setId(id);
		
		updatedObj = service.update(task,updatedObj);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<TaskDTO>> findAll() {
		
		List<Task> list = service.findAll();
		
		List<TaskDTO> listDTO = list.stream().map(
				obj -> new TaskDTO(obj)).collect(Collectors.toList()
		);	
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@RequestMapping(value="/byProject", method = RequestMethod.GET)
	public ResponseEntity<List<TaskDTO>> findByProjectId(@RequestParam(value="projectId") Integer projectId) {
		
		List<Task> list = service.findByProjectId(projectId);
		
		List<TaskDTO> listDTO = list.stream().map(
				obj -> new TaskDTO(obj)).collect(Collectors.toList()
		);	
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}

}
