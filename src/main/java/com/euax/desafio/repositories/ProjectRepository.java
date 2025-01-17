package com.euax.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.euax.desafio.domain.Project;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

}



