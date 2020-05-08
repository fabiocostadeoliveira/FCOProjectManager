package com.euax.desafio.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.euax.desafio.domain.Project;
import com.euax.desafio.dto.ProjectDTO;
import com.euax.desafio.resources.exceptions.FieldMessage;
import com.euax.desafio.services.ProjectService;
import com.euax.desafio.utils.RequestUtil;

public class ProjectSaveValidator implements ConstraintValidator<ProjectSave, ProjectDTO>{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ProjectService projectService;

	@Override
	public boolean isValid(ProjectDTO value, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		
		Project projectAux = null;
		
		Integer projectId = RequestUtil.getIntegerURLParamFromRequest(request, "id", null);
		
		if (projectId != null) {

			projectAux = projectService.findWithoutValidation(projectId);
		}
		
		if (projectAux == null ) {
			
			list.addAll(getValidationsErrosNewProject(value));
			
		}else {
			
			list.addAll(getValidationsErrosUpdateProject(value, projectAux));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
	
		return list.isEmpty();
	}
	
	
	private List<FieldMessage> getValidationsErrosNewProject(ProjectDTO value){
		
		List<FieldMessage> list = new ArrayList<>();
		
		if(value.getStartDate().after(value.getEndDate())) {
			list.add(new FieldMessage("endDate","Data de fim do projeto nao pode ser menor que a data inicial."));
		}
			
		return list;
	}
	
	private List<FieldMessage> getValidationsErrosUpdateProject(ProjectDTO value, Project oldValue){
		
		List<FieldMessage> list = new ArrayList<>();
		
		if(value.getStartDate().after(value.getEndDate())) {
			list.add(new FieldMessage("endDate","Data de fim do projeto nao pode ser menor que a data inicial."));
		}
		
		return list;
	}

}
