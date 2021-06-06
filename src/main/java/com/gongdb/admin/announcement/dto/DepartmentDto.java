package com.gongdb.admin.announcement.dto;

import com.gongdb.admin.announcement.entity.Department;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentDto {

    private Long id;
    private String name;

    @Builder
    private DepartmentDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public static DepartmentDto of(Department department) {
        return DepartmentDto.builder().id(department.getId()).name(department.getName()).build();
    }
}
