package com.gongdb.admin.announcement.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

public class AnnouncementInputFormDto {
    
    @NotNull
    private String companyName;

    @NotNull
    private String positionName;

    @NotNull
    private String recruitType;

    @NotNull
    private String recruitLevel;

    @NotNull
    private String workingType;

    @NotNull
    private String districtName;

    @NotNull
    private int headCount;

    private List<String> certificates;
    private List<String> departments;
    private List<String> subjects;
    private List<LanguageScoreInputDto> languageScores;
    private List<String> notes;
    private LocalDateTime receiptTimestamp;
    private String sequence;
    private String link;
    private String rank;
}
