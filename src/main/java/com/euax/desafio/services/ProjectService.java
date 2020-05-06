package com.euax.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.euax.desafio.domain.Project;
import com.euax.desafio.dto.ProjectDTO;
import com.euax.desafio.repositories.ProjectRepository;
import com.euax.desafio.services.exceptions.IntegrityViolationException;
import com.euax.desafio.services.exceptions.ObjectNotFoundException;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository repository;
	
	
	public Project find(Integer id) {

		Optional<Project> project = repository.findById(id);
		
		return project.orElseThrow(() -> new ObjectNotFoundException("Projeto nao encontrado - id: " + id));
		
	}
	
	
	public Project insert(Project obj) {
		
		obj.setId(null);
		
		return repository.save(obj);
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

}
