package com.example.programming.dmaker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.programming.dmaker.code.StatusCode;
import com.example.programming.dmaker.dto.CreateDeveloper;
import com.example.programming.dmaker.dto.DeveloperDetailDto;
import com.example.programming.dmaker.dto.DeveloperDto;
import com.example.programming.dmaker.dto.EditDeveloper;
import com.example.programming.dmaker.entity.Developer;
import com.example.programming.dmaker.entity.RetiredDeveloper;

import static com.example.programming.dmaker.exception.DMakerErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import com.example.programming.dmaker.exception.DMakerException;
import com.example.programming.dmaker.repository.DeveloperRepository;
import com.example.programming.dmaker.repository.RetiredDeveloperRepository;
import com.example.programming.dmaker.type.DeveloperLevel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        // business logic start
        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .statusCode(StatusCode.EMPLOYED)
                .age(request.getAge())
                .memberId(request.getMemberId())
                .build();
        developerRepository.save(developer); // DB에 저장 영속화

        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // business validation
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));
    }

    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream()
                .map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    public void validateEditDeveloperRequest(EditDeveloper.Request request, String memberId) {

        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel,
            Integer experienYears) {
        if (developerLevel == DeveloperLevel.SENIOR
                && experienYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if (developerLevel == DeveloperLevel.JUNGNIOR &&
                (experienYears < 4 ||
                        experienYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if (developerLevel == DeveloperLevel.JUNIOR && experienYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateEditDeveloperRequest(request, memberId);

        // 해당 MemberId로 Developer가 있어야한다.
        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(
                () -> new DMakerException(NO_DEVELOPER));

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        // 1. EMPLOYED -> RETIRED
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);

        /*
         * 아래 if문 처럼 작성하면 트랜잭션을 하다가 예외가 걸려서 RetiredDeveloper을 저장하지 못하면 @Transactional
         * 어노테이션이 롤백을 지원해준다.
         * 하지만 @Transactional 설정을 안해놓는다면
         * Developer table 에서는 트랜잭션이 성공하고
         * RetiredDeveloper table 에서는 저장이 안되는 롤백이 안되는 일이 일어난다.....
         * Atomic이 깨져버리는거다.
         * 꼭 하나의 트랜잭션이라도 @Transactional 을 사용하자.
         * 
         * if(developer != null) throw new DMakerException(NO_DEVELOPER)
         * 
         */

        // 2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();
        retiredDeveloperRepository.save(retiredDeveloper);

        return DeveloperDetailDto.fromEntity(developer);
    }

}
