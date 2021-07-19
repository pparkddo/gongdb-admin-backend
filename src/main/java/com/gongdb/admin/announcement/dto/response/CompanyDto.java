package com.gongdb.admin.announcement.dto.response;

import com.gongdb.admin.announcement.entity.Company;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyDto {
    
    private Long id;
    private String name;

    @Builder
    private CompanyDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CompanyDto of(Company company) {
        return CompanyDto.builder().id(company.getId()).name(company.getName()).build();
    }
}
