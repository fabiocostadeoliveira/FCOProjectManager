package com.euax.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.euax.desafio.domain.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>  {
	
	public List<Task> findByProjectId(Integer projectId);
	
	@Deprecated
	@Query("select count(*) from Task t inner join t.project p where p.id = :projectId")
	public Long countTasksByProjectId(Integer projectId);
	
	@Deprecated
	@Query("select t from Task t where t.endDate = ( SELECT MAX(st.endDate) from Task st inner join st.project p where p.id = :projectId)")
	public Task findlastTaskByProjectId(Integer projectId);
	
}
