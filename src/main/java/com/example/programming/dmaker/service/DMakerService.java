package com.example.programming.dmaker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.programming.dmaker.dto.CreateDeveloper;
import com.example.programming.dmaker.dto.DeveloperDetailDto;
import com.example.programming.dmaker.dto.DeveloperDto;
import com.example.programming.dmaker.entity.Developer;
import static com.example.programming.dmaker.exception.DMakerErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import com.example.programming.dmaker.exception.DMakerException;
import com.example.programming.dmaker.repository.DeveloperRepository;
import com.example.programming.dmaker.type.DeveloperLevel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        // business logic start
        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .age(request.getAge())
                .memberId(request.getMemberId())
                .build();
        developerRepository.save(developer); // DB에 저장 영속화

        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // business validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienYears = request.getExperienceYears();

        if (developerLevel == DeveloperLevel.SENIOR
                && experienYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if (request.getDeveloperLevel() == DeveloperLevel.JUNGNIOR &&
                experienYears < 4 ||
                experienYears > 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if (developerLevel == DeveloperLevel.JUNIOR && experienYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));
    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll()
                .stream()
                .map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }
}
