package com.example.programming.dmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.programming.dmaker.entity.Developer;

/**
 * DeveloperRepository
 */
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}