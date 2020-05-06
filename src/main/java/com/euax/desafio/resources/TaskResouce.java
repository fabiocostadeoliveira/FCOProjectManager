package com.euax.desafio.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.euax.desafio.domain.Project;
import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.TaskDTO;
import com.euax.desafio.services.ProjectService;
import com.euax.desafio.services.TaskService;

//TODO COLOCAR NO PATH projectId
@RestController
@RequestMapping(value = "/{projectId}/tasks")
public class TaskResouce {
	
	@Autowired
	private TaskService service;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Task> find(@PathVariable Integer projectId, @PathVariable Integer id) {
		
		Task task = service.find(id);
		return ResponseEntity.ok().body(task);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@PathVariable Integer projectId, @Valid @RequestBody TaskDTO objDto){
		
		Project project = projectService.find(projectId);
		
		Task obj = service.fromDTO(objDto);
		
		obj.setProject(project);
		
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(objDto.getId())
				.toUri(); 
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}/", method = RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody TaskDTO objDto, @PathVariable Integer projectId){
		
		Task obj = service.fromDTO(objDto);
		
		// TODO busca projeto no service do projeto
		//obj.setId(id);
		
		//obj = service.update(obj);
		
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
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}

	
	

}
