package com.euax.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.euax.desafio.domain.Project;
import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.ProjectDTO;
import com.euax.desafio.repositories.ProjectRepository;
import com.euax.desafio.services.exceptions.IntegrityViolationException;
import com.euax.desafio.services.exceptions.ObjectNotFoundException;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository repository;
	
	
	public Project findWithoutValidation(Integer id) {

		Optional<Project> project = repository.findById(id);
		
		return project.orElse(null);
	}
	
	
	public Project find(Integer id) {

		Optional<Project> project = repository.findById(id);
		
		return project.orElseThrow(() -> new ObjectNotFoundException("Projeto nao encontrado - id: " + id));
	}

	
	public Project insert(Project obj) {
		
		obj.setId(null);
		
		return repository.save(obj);
	}
	
	
	public Project update(Project existingObj, Project newObj) {
		
		updateData(existingObj, newObj);
		
		return repository.save(existingObj);
	}
	
	public void delete(Integer id) {
		
		find(id);
		
		try {
			repository.deleteById(id);
			
		} catch (DataIntegrityViolationException dive) {
			
			throw new IntegrityViolationException("Nao é possivel deletar um projeto que contem tarefas vinculadas.");
			
		}
		
	}
	
	public List<Project> findAll() {
		
		return repository.findAll();
	}
	
	
	public Project fromDTO(ProjectDTO projectDTO) {
		return new Project(projectDTO.getId(),
							projectDTO.getName(),
							projectDTO.getStartDate(),
							projectDTO.getEndDate());
	}
	
	
/*
	public Project fromUpdateDTO(ProjectDTO objDTO) {
		
		return new Project(null,objDTO.getName(),objDTO.getStartDate(),objDTO.getEndDate());
	}
	*/
	
	private void updateData(Project oldObj, Project newObj) {
		oldObj.setName(newObj.getName());
		oldObj.setStartDate(newObj.getStartDate());
		oldObj.setEndDate(newObj.getEndDate());
	}

	

}
