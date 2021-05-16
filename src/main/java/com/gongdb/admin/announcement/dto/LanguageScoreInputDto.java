package com.gongdb.admin.announcement.dto;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LanguageScoreInputDto {
    
    @NotNull
    private String name;

    @NotNull
    private String score;

    @Builder
    private LanguageScoreInputDto(String name, String score) {
        this.name = name;
        this.score = score;
    }
}
