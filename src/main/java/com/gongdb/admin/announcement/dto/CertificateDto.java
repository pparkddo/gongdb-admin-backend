package com.gongdb.admin.announcement.dto;

import com.gongdb.admin.announcement.entity.Certificate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CertificateDto {

    private Long id;
    private String name;

    @Builder
    private CertificateDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public static CertificateDto of(Certificate certificate) {
        return CertificateDto.builder().id(certificate.getId()).name(certificate.getName()).build();
    }
}
