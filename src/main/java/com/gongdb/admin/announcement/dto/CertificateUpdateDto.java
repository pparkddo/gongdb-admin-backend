package com.gongdb.admin.announcement.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CertificateUpdateDto {
    
    @NotEmpty
    private String name;

    @Builder
    private CertificateUpdateDto(String name) {
        this.name = name;
    }
}
