package com.example.programming.dmaker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.programming.dmaker.entity.Developer;
import com.example.programming.dmaker.repository.DeveloperRepository;
import com.example.programming.dmaker.type.DeveloperLevel;
import com.example.programming.dmaker.type.DeveloperSkillType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper() {
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONTEND)
                .experienceYears(2)
                .name("Olaf")
                .age(5)
                .memberId("gwmein")
                .build();
        developerRepository.save(developer); // DB에 저장 영속화
    }
}
