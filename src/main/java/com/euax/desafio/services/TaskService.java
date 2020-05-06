package com.euax.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.TaskDTO;
import com.euax.desafio.repositories.TaskRepository;
import com.euax.desafio.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	public Task find(Integer id) {

		Optional<Task> task = repository.findById(id);
		
		return task.orElseThrow(() -> new ObjectNotFoundException("Task não encontrada - id: " + id));
		
	}
	
	
	public Task insert(Task obj) {
		
		obj.setId(null);
		
		return repository.save(obj);
	}
	
	
	public void delete(Integer id) {
		
		find(id);
		
		try {
			repository.deleteById(id);
			
		} catch (DataIntegrityViolationException dive) {
			
			//TODO verificar se tem alguma integridade, caso contrario, pica a porva
			
		}
		
	}
	
	public List<Task> findAll() {
		
		return repository.findAll();
	}
	
	
	public Task fromDTO(TaskDTO taskDTO) {
		return new Task();
	}

	
	

}
