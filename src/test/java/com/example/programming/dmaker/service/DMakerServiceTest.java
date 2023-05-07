package com.example.programming.dmaker.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.programming.dmaker.dto.CreateDeveloper;
import com.example.programming.dmaker.dto.DeveloperDto;
import com.example.programming.dmaker.type.DeveloperLevel;
import com.example.programming.dmaker.type.DeveloperSkillType;

@SpringBootTest
public class DMakerServiceTest {
    /*
     * Test Code 에서 New 연산자를 사용해서 불러오지 않으며 Bean으로 등록을 해놨기 때문에
     * SpringBootTest 어노테이션을 이용하면 어플리케이션을 실행한것과 비슷하게 모든 Bean을 다 띄어서 테스트를 돌려보는것이다.
     * 통합테스트 라고도 한다.
     * 이렇게 하게되면 격리성이 떨어지고 DB에 데이터가 있어야 가능한 테스트이다.
     * 그래서 모킹 데이터 가 필요하다. Mockito 를 사용해보자
     */
    @Autowired
    private DMakerService dMakerService;

    @Test
    public void testSomething() {
        dMakerService.createDeveloper(CreateDeveloper.Request.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.FULL_STACK)
                .experienceYears(13)
                .memberId("memberId")
                .name("tony")
                .age(32)
                .build());
        List<DeveloperDto> allEmployedDeveloper = dMakerService.getAllEmployedDeveloper();
        System.out.println("===========================");
        System.out.println(allEmployedDeveloper);
        System.out.println("===========================");

    }
}
