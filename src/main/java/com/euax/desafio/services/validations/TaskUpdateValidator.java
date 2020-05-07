package com.euax.desafio.services.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.euax.desafio.domain.Task;
import com.euax.desafio.dto.TaskUpdateDTO;
import com.euax.desafio.resources.exceptions.FieldMessage;
import com.euax.desafio.services.TaskService;

public class TaskUpdateValidator implements ConstraintValidator<TaskUpdate, TaskUpdateDTO>{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	TaskService taskService;

	@Override
	public boolean isValid(TaskUpdateDTO value, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String> ) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Integer taskId = Integer.parseInt(map.get("id"));
		
		Task taskAux = taskService.find(taskId);
		
		if(value.getEndDate().before(taskAux.getStartDate())) {
			list.add(new FieldMessage("endDate","Data fim da task deve ser maior ou igual a data de inicio."));
		}
		
		if(taskAux.getProject().getEndDate() != null && value.isFinished()) {
			list.add(new FieldMessage("finished","Task nao pode ser finalizada pois o projeto ja foi encerrado."));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
	
		return list.isEmpty();
	}

}
