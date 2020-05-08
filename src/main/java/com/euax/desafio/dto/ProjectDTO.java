package com.euax.desafio.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.euax.desafio.domain.Project;
import com.euax.desafio.services.validations.ProjectSave;
import com.fasterxml.jackson.annotation.JsonFormat;

@ProjectSave
public class ProjectDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Prenchimento Obrigatorio.")
	@Length(min = 5, max = 30, message = "O tamanho deve ser entre 5 e 30 caracteres.")
	private String name;
	
	@NotNull(message = "Prenchimento Obrigatorio.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	
	@NotNull(message = "Prenchimento Obrigatorio.")
	@JsonFormat(pattern = "dd/MM/yyyy")
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
