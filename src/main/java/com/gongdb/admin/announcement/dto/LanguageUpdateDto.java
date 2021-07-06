package com.gongdb.admin.announcement.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LanguageUpdateDto {
    
    @NotEmpty
    private String name;

    @Builder
    private LanguageUpdateDto(String name) {
        this.name = name;
    }
}
