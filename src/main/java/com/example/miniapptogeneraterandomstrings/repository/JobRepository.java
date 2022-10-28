package com.example.miniapptogeneraterandomstrings.repository;

import com.example.miniapptogeneraterandomstrings.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends  JpaRepository<Job, Long>{ }