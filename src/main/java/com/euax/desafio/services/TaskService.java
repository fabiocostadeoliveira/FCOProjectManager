package com.euax.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.euax.desafio.domain.Project;
import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.TaskDTO;
import com.euax.desafio.repositories.TaskRepository;
import com.euax.desafio.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	@Autowired
	private ProjectService projectService;
	
	public Task findWithoutValidation(Integer id) {

		Optional<Task> task = repository.findById(id);
		
		return task.orElse(null);
	}

	
	public Task find(Integer id) {

		Optional<Task> task = repository.findById(id);
		
		return task.orElseThrow(() -> new ObjectNotFoundException("Task não encontrada - id: " + id));
	}
	

	public Task insert(Task obj) {
		
		obj.setId(null);
		
		return repository.save(obj);
	}
	
	
	public Task update(Task newObj) {
		
		Task obj = find(newObj.getId());
		
		updateData(obj, newObj);
		
		return repository.save(newObj);
	}
	
	public Task update(Task existingObj, Task newObj) {
		
		updateData(existingObj, newObj);
		
		return repository.save(existingObj);
	}
	
	public void delete(Integer id) {
		
		find(id);
		
		repository.deleteById(id);
	}
	
	public List<Task> findByProjectId(Integer projectId) {
		
		return repository.findByProjectId(projectId);
	}
	
	public List<Task> findAll() {
		
		return repository.findAll();
	}
	
	
	public Task fromDTO(TaskDTO taskDTO) {
		
		Project project = projectService.find(taskDTO.getProjectId());
		
		return new Task(taskDTO.getId(),
						taskDTO.getName(),
						taskDTO.getStartDate(),
						taskDTO.getEndDate(),
						taskDTO.isFinished(),
						project);
	}
	
	public Task fromUpdateDTO(TaskDTO taskDTO) {
		
		return new Task(null,taskDTO.getName(),taskDTO.getStartDate(),taskDTO.getEndDate(),taskDTO.isFinished(),null);
		
	}
	
	private void updateData(Task oldObj, Task newObj) {
		oldObj.setName(newObj.getName());
		oldObj.setStartDate(newObj.getStartDate());
		oldObj.setEndDate(newObj.getEndDate());
		oldObj.setFinished(newObj.isFinished());
	}

}
