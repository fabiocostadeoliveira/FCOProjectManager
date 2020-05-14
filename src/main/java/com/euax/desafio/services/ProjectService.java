package com.euax.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.euax.desafio.domain.Project;
import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.ProjectDTO;
import com.euax.desafio.dto.ProjectDetailsDTO;
import com.euax.desafio.repositories.ProjectRepository;
import com.euax.desafio.services.exceptions.IntegrityViolationException;
import com.euax.desafio.services.exceptions.ObjectNotFoundException;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository repository;
	
	@Autowired
	TaskService taskService;
	
	
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
	
	
	public ProjectDetailsDTO details(Integer projectId) {
		
		ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
		
		Project project = find(projectId);
		
		List<Task> listTasks = taskService.findByProjectId(projectId);
		
		Integer totalTasks = listTasks.size();
		
		Task lastTask = getLastTaskFromList(listTasks);
		
		projectDetailsDTO.setTotalTasks(totalTasks);
		projectDetailsDTO.setWillBeLate(willBeLate(project, lastTask));
		projectDetailsDTO.setCompletedPercentage(getCompletedPercentage(listTasks));
		
		return projectDetailsDTO;
	}
	
	
	private double getCompletedPercentage(List<Task> listTasks) {
		
		Integer total = listTasks.size();
		
		Integer totalCompleted = listTasks
								.stream()
								.filter(task -> task.isFinished())
								.map(t -> new Integer(1))
								.reduce(0, Integer::sum );
		
		
		Double perc = calculateCompletePercentage(total, totalCompleted);
		
		return perc;
	}
	
	private Task getLastTaskFromList(List<Task> listTasks) {
		
		Task task = listTasks.stream().reduce(Task::maxDate).get();
		
		return task;
	}
	
	
	private double calculateCompletePercentage(Integer totalTasks, Integer totalCompleted) {
		
		Double perc = null;
		
		try {
			
			perc = ( new Double(totalCompleted * 100 ) / new Double(totalTasks));
		} catch (Exception e) {
		
			perc = new Double(0);
		}
		
		return perc;
	}
	
	private boolean willBeLate(Project project, Task lastTask) {
		
		if(lastTask == null)
			return false;
		
		if(lastTask.getEndDate().after(project.getEndDate()))
			return true;
		
		return false;
	}
	
	
	public Project fromDTO(ProjectDTO projectDTO) {
		return new Project(projectDTO.getId(),
							projectDTO.getName(),
							projectDTO.getStartDate(),
							projectDTO.getEndDate());
	}
	
	
	private void updateData(Project oldObj, Project newObj) {
		oldObj.setName(newObj.getName());
		oldObj.setStartDate(newObj.getStartDate());
		oldObj.setEndDate(newObj.getEndDate());
	}

	

}
