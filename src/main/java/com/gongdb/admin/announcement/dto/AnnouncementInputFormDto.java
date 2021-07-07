package com.gongdb.admin.announcement.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.gongdb.admin.global.validation.UniqueLanguageScoreInput;
import com.gongdb.admin.global.validation.UniqueValue;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementInputFormDto {
    
    @NotEmpty private String companyName;
    @NotEmpty private String positionName;
    @NotEmpty private String recruitType;
    @NotEmpty private String recruitLevel;
    @NotEmpty private String workingType;
    @NotEmpty private String districtName;
    @NotEmpty private String headCount;
    @NotNull private LocalDateTime receiptTimestamp;

    @Valid
    @UniqueValue
    private List<@NotEmpty String> certificates = new ArrayList<>();

    @Valid
    @UniqueValue
    private List<@NotEmpty String> departments = new ArrayList<>();

    @Valid
    @UniqueValue
    private List<@NotEmpty String> subjects = new ArrayList<>();

    @Valid
    @UniqueLanguageScoreInput
    private List<@Valid LanguageScoreInputDto> languageScores = new ArrayList<>();
 
    @Valid
    @UniqueValue
    private List<@NotEmpty String> notes = new ArrayList<>();

    private String sequence;
    private String link;
    private String rank;

    @Builder
    private AnnouncementInputFormDto(String companyName, String positionName, String recruitType,
                                     String recruitLevel, String workingType, String districtName,
                                     String headCount, List<String> certificates, List<String> departments,
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
