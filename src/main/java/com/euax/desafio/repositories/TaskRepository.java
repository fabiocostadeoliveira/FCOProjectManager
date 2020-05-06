package com.euax.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.euax.desafio.domain.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>  {

}
