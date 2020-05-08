package com.euax.desafio.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.euax.desafio.domain.Project;
import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.TaskDTO;
import com.euax.desafio.resources.exceptions.FieldMessage;
import com.euax.desafio.services.ProjectService;
import com.euax.desafio.services.TaskService;
import com.euax.desafio.utils.RequestUtil;

public class TaskSaveValidator implements ConstraintValidator<TaskSave, TaskDTO>{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ProjectService projectService;


	@Override
	public boolean isValid(TaskDTO value, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Task taskAux = null;
		
		Integer taskId = RequestUtil.getIntegerURLParamFromRequest(request, "id", null);
		
		if (taskId != null) {

			taskAux = taskService.findWithoutValidation(taskId);
		}
		
		if (taskAux == null ) {
			
			list.addAll(getValidationsErrosNewTask(value));
			
		}else {
			
			list.addAll(getValidationsErrosUpdateTask(value, taskAux));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
	
		return list.isEmpty();
	}
	
	
	private List<FieldMessage> getValidationsErrosNewTask(TaskDTO value){
		
		List<FieldMessage> list = new ArrayList<>();
		
		Project project = null; 
		
		if (value.getProjectId() == null) {
			
			list.add(new FieldMessage("projectId","Id do projeto nao informado"));
		}else {
			
			project = projectService.findWithoutValidation(value.getProjectId());
			
			if(project != null) {
				
				if(value.getStartDate().before(project.getStartDate())) {
					list.add(new FieldMessage("startDate","Data de inicio da task nao pode ser menor que a data inicial do projeto"));
				}
				
				if(value.getStartDate().after(value.getEndDate())) {
					list.add(new FieldMessage("endDate","Data de fim da task nao pode ser menor que a data inicial."));
				}
				
			}else {
				list.add(new FieldMessage("projectId","Projeto não encontrado" ));
			}
		}
		
		return list;
	}
	
	private List<FieldMessage> getValidationsErrosUpdateTask(TaskDTO value, Task oldValue){
		
		List<FieldMessage> list = new ArrayList<>();
		
		if(value.getEndDate().before(oldValue.getStartDate())) {
			list.add(new FieldMessage("endDate","Data fim da task deve ser maior ou igual a data de inicio."));
		}
		
		return list;
	}
	
}
