package com.euax.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.euax.desafio.domain.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>  {
	
	public List<Task> findByProjectId(Integer projectId);
}
