package com.gongdb.admin.announcement.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyUpdateDto {
    
    @NotEmpty
    private String name;

    @Builder
    private CompanyUpdateDto(String name) {
        this.name = name;
    }
}
