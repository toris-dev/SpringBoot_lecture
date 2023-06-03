package com.example.programming.dmaker.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.programming.dmaker.dto.DeveloperDto;
import com.example.programming.dmaker.service.DMakerService;
import com.example.programming.dmaker.type.DeveloperLevel;
import com.example.programming.dmaker.type.DeveloperSkillType;

/**
 * 원하는 Controller들만 테스트할 수 있다.
 */
@WebMvcTest(DMakerController.class)
public class DMakerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DMakerService dMakerService;

  protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

  @Test
  void getAllDeveopers() throws Exception {
    DeveloperDto juniordeveloperDto =
        DeveloperDto.builder().developerSkillType(DeveloperSkillType.BACK_END)
            .developerLevel(DeveloperLevel.JUNIOR).memberId("memberid1").build();
    DeveloperDto seniordeveloperDto =
        DeveloperDto.builder().developerSkillType(DeveloperSkillType.FRONT_END)
            .developerLevel(DeveloperLevel.SENIOR).memberId("memberid2").build();

    given(dMakerService.getAllEmployedDevelopers())
        .willReturn(Arrays.asList(juniordeveloperDto, seniordeveloperDto));
    mockMvc.perform(get("/developers").contentType(contentType)).andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.[0].developerSkillType", is(DeveloperSkillType.BACK_END.name())))
        .andExpect(jsonPath("$.[0].developerLevel", is(DeveloperLevel.JUNIOR.name())))
        .andExpect(jsonPath("$.[1].developerSkillType", is(DeveloperSkillType.FRONT_END.name())))
        .andExpect(jsonPath("$.[1].developerLevel", is(DeveloperLevel.SENIOR.name())));
  }

}
