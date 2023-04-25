package com.example.programming.dmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeveloperSkillType {
    BACKEND("백엔드 개발자"),
    FRONTEND("프론트 개발자"),
    FULLSTACK("풀스택 개발자");

    private final String description;
}
