package com.euax.desafio.dto;

public class ProjectDetailsDTO {
	
	private double completedPercentage;
	
	private Integer totalTasks;
	
	private boolean willBeLate;
	
	
	public ProjectDetailsDTO() {
		
	}

	public double getCompletedPercentage() {
		return completedPercentage;
	}

	public void setCompletedPercentage(double completedPercentage) {
		this.completedPercentage = completedPercentage;
	}

	public Integer getTotalTasks() {
		return totalTasks;
	}

	public void setTotalTasks(Integer totalTasks) {
		this.totalTasks = totalTasks;
	}

	public boolean isWillBeLate() {
		return willBeLate;
	}

	public void setWillBeLate(boolean willBeLate) {
		this.willBeLate = willBeLate;
	}

}
