package com.euax.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.euax.desafio.domain.Project;
import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.TaskDTO;
import com.euax.desafio.dto.TaskUpdateDTO;
import com.euax.desafio.repositories.TaskRepository;
import com.euax.desafio.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	@Autowired
	private ProjectService projectService;
	
	public Task find(Integer id) {

		Optional<Task> task = repository.findById(id);
		
		return task.orElseThrow(() -> new ObjectNotFoundException("Task não encontrada - id: " + id));
		
	}
	
	
	@Transactional
	public Task insert(Task obj) {
		
		obj.setId(null);
		
		return repository.save(obj);
	}
	
	public Task update(Task obj) {
		
		Task newObj = find(obj.getId());
		
		updateData(newObj, obj);
		
		return repository.save(newObj);
	}
	
	
	public void delete(Integer id) {
		
		find(id);
		
		try {
			repository.deleteById(id);
			
		} catch (DataIntegrityViolationException dive) {
			
			//TODO verificar se tem alguma integridade, caso contrario, pica a porva
			
		}
		
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
	
	public Task fromDTO(TaskUpdateDTO taskUpdateDTO) {
		
		return new Task(null,taskUpdateDTO.getName(),null,taskUpdateDTO.getEndDate(),taskUpdateDTO.isFinished(),null);
		
	}
	
	private void updateData(Task newObj, Task obj) {
		newObj.setName(obj.getName());
		newObj.setEndDate(obj.getEndDate());
		newObj.setFinished(obj.isFinished());
	}

	
	

}
