package com.gongdb.admin.announcement.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private List<String> certificates = new ArrayList<>();
    private List<String> departments = new ArrayList<>();
    private List<String> subjects = new ArrayList<>();
    private List<LanguageScoreInputDto> languageScores = new ArrayList<>();
    private List<String> notes = new ArrayList<>();
    private LocalDateTime receiptTimestamp;
    private String sequence;
    private String link;
    private String rank;

    @Builder
    private AnnouncementInputFormDto(String companyName, String positionName, String recruitType,
                                     String recruitLevel, String workingType, String districtName,
                                     int headCount, List<String> certificates, List<String> departments,
                                     List<String> subjects, List<LanguageScoreInputDto> languageScores,
                                     List<String> notes, LocalDateTime receiptTimestamp,
                                     String sequence, String link, String rank) {
        this.companyName = companyName;
        this.positionName = positionName;
        this.recruitType = recruitType;
        this.recruitLevel = recruitLevel;
        this.workingType = workingType;
        this.districtName = districtName;
        this.headCount = headCount;
        this.certificates = certificates;
        this.departments = departments;
        this.subjects = subjects;
        this.languageScores = languageScores;
        this.notes = notes;
        this.receiptTimestamp = receiptTimestamp;
        this.sequence = sequence;
        this.link = link;
        this.rank = rank;
    }
}
