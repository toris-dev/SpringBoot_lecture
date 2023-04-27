package com.example.programming.dmaker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.programming.dmaker.dto.CreateDeveloper;
import com.example.programming.dmaker.dto.DeveloperDetailDto;
import com.example.programming.dmaker.dto.DeveloperDto;
import com.example.programming.dmaker.service.DMakerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");
        return dMakerService.getAllDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperdetail(@PathVariable String memberId) {
        log.info("GET /developers/{memberId} HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request) { // @Valid 를 사용함으로 써 메소드에 진입하기전에 메소드notException이 발생
        log.info("request: {}", request);

        return dMakerService.createDeveloper(request);

    }
}
