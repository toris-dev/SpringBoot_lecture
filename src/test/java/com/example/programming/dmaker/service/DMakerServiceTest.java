package com.example.programming.dmaker.service;

import static com.example.programming.dmaker.type.DeveloperLevel.SENIOR;
import static com.example.programming.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.programming.dmaker.code.StatusCode;
import com.example.programming.dmaker.dto.DeveloperDetailDto;
import com.example.programming.dmaker.entity.Developer;
import com.example.programming.dmaker.repository.DeveloperRepository;
import com.example.programming.dmaker.repository.RetiredDeveloperRepository;

/**
 * Mockito를 이용하여 Test Code 작성
 */

@ExtendWith(MockitoExtension.class)
public class DMakerServiceTest {
    @Mock // 가상의 Mock으로 서비스 테스트 등록
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks // 위에 2개의 Mock을 넣어준다.
    private DMakerService dMakerService;

    @Test
    public void testSomething() {
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(Developer.builder()
                        .developerLevel(SENIOR)
                        .developerSkillType(FRONT_END)
                        .experienceYears(13)
                        .statusCode(StatusCode.EMPLOYED)
                        .name("name")
                        .age(12)
                        .build()));

        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        assertEquals(SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END, developerDetail.getDeveloperSkillType());
        assertEquals(13, developerDetail.getExperienceYears());

    }
}
