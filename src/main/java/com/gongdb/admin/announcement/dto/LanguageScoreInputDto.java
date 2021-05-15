package com.gongdb.admin.announcement.dto;

import javax.validation.constraints.NotNull;

public class LanguageScoreInputDto {
    
    @NotNull
    private String name;

    @NotNull
    private String score;
}
