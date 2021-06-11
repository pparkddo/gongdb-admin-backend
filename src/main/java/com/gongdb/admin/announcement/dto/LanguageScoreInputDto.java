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

    private String perfectScore;

    @Builder
    private LanguageScoreInputDto(String name, String score, String perfectScore) {
        this.name = name;
        this.score = score;
        this.perfectScore = perfectScore;
    }
}
