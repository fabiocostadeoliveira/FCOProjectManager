package com.euax.desafio.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.euax.desafio.domain.Project;

public class ProjectDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Prenchimento Obrigatorio.")
	@Length(min = 5, max = 30, message = "O tamanho deve ser entre 5 e 30 caracteres.")
	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	public ProjectDTO() {
	}
	
	public ProjectDTO(Project project) {
		this.id = project.getId();
		this.name = project.getName();
		this.startDate = project.getStartDate();
		this.endDate = project.getEndDate();
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
