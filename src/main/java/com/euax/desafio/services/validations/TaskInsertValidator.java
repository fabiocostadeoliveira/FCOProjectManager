package com.euax.desafio.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.euax.desafio.domain.Project;
import com.euax.desafio.dto.TaskDTO;
import com.euax.desafio.resources.exceptions.FieldMessage;
import com.euax.desafio.services.ProjectService;

public class TaskInsertValidator implements ConstraintValidator<TaskInsert, TaskDTO>{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ProjectService projectService;

	@Override
	public boolean isValid(TaskDTO value, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Project project = null; 
		
		if (value.getProjectId() == null) {
			
			list.add(new FieldMessage("projectId","Id do projeto nao informado"));
		}else {
			
			project = projectService.findWithoutValidation(value.getProjectId());
		}
		
		if(project != null) {
			
			if(value.getStartDate().before(project.getStartDate())) {
				list.add(new FieldMessage("startDate","Data de inicio da task nao pode ser menor que a data inicial do projeto"));
			}
			
			if(project.getEndDate() != null) {
				list.add(new FieldMessage("projectId","Task não pode ser inserida, projeto ja foi finalizado!"));
			}
			
		}else {
			list.add(new FieldMessage("projectId","Projeto não encontrado" ));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
	
		return list.isEmpty();
	}

}
