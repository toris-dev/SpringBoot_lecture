package com.example.programming.dmaker.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.programming.dmaker.service.DMakerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");
        return Arrays.asList("snow", "elsa", "Olaf");
    }

    @GetMapping("/create-developer")
    public List<String> createDevelopers() {
        log.info("GET /create-developer HTTP/1.1");

        dMakerService.createDeveloper();

        return Collections.singletonList("Olaf");
    }
}
