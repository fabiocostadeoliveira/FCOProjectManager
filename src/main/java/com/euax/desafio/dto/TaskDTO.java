package com.euax.desafio.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.euax.desafio.domain.Task;
import com.euax.desafio.services.validations.TaskSave;
import com.fasterxml.jackson.annotation.JsonFormat;

@TaskSave
public class TaskDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Prenchimento Obrigatorio.")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres.")
	private String name;
	
	@NotNull(message = "Prenchimento Obrigatorio.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	
	@NotNull(message = "Prenchimento Obrigatorio.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	
	private boolean finished;
	
	private Integer projectId;
	
	public TaskDTO() {
	}
	
	public TaskDTO(Task task) {
		this.id = task.getId();
		this.name = task.getName();
		this.startDate = task.getStartDate();
		this.endDate = task.getEndDate();
		this.finished = task.isFinished();
		this.projectId = task.getProject() != null ? task.getProject().getId() : null;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

}
