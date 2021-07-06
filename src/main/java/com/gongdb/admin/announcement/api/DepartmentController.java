package com.gongdb.admin.announcement.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.DepartmentDto;
import com.gongdb.admin.announcement.dto.DepartmentUpdateDto;
import com.gongdb.admin.announcement.service.DepartmentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PutMapping("/{id}")
    public Map<String, String> updateDepartment(
            @PathVariable Long id, @Valid @RequestBody DepartmentUpdateDto departmentUpdateDto) {
        departmentService.update(id, departmentUpdateDto);
        return Collections.singletonMap("response", "ok");
    }
}
