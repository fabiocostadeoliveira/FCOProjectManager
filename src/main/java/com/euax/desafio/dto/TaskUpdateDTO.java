package com.euax.desafio.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.euax.desafio.domain.Task;
import com.euax.desafio.services.validations.TaskUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;


@TaskUpdate
public class TaskUpdateDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Prenchimento Obrigatorio.")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres.")
	private String name;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	
	private boolean finished;
	
	public TaskUpdateDTO() {
	}
	
	public TaskUpdateDTO(Task task) {
		this.id = task.getId();
		this.name = task.getName();
		this.endDate = task.getEndDate();
		this.finished = task.isFinished();
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

}
