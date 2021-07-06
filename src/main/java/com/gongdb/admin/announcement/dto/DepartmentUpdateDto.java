package com.gongdb.admin.announcement.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentUpdateDto {
    
    @NotEmpty
    private String name;

    @Builder
    private DepartmentUpdateDto(String name) {
        this.name = name;
    }
}
