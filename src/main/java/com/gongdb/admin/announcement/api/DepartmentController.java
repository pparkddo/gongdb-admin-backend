package com.gongdb.admin.announcement.api;

import java.util.List;

import com.gongdb.admin.announcement.dto.DepartmentDto;
import com.gongdb.admin.announcement.service.DepartmentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {
    
    private final DepartmentService departmentService;
    
    @GetMapping
    public List<DepartmentDto> getDepartments() {
        return departmentService.getAll();
    }
}
