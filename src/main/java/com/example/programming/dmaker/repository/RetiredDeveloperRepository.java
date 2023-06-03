package com.example.programming.dmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.programming.dmaker.entity.RetiredDeveloper;

/**
 * retiredDeveloperRepository
 */
@Repository
public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper, Long> {
}
