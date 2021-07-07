package com.gongdb.admin.announcement.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class LanguageScoreInputDto {
    
    @NotEmpty
    private String name;

    @NotEmpty
    private String score;

    private String perfectScore;

    @Builder
    private LanguageScoreInputDto(String name, String score, String perfectScore) {
        this.name = name;
        this.score = score;
        this.perfectScore = perfectScore;
    }
}
