package com.example.programming.dmaker.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.programming.dmaker.code.StatusCode;
import com.example.programming.dmaker.entity.Developer;

/**
 * DeveloperRepository
 */
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
  Optional<Developer> findByMemberId(String memberId);

  List<Developer> findDevelopersByStatusCodeEquals(StatusCode statusCode);
}
