package com.itacademy.database.repository;

import com.itacademy.database.entity.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {
}