package com.gongdb.admin.announcement.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.request.DepartmentUpdateDto;
import com.gongdb.admin.announcement.dto.response.DepartmentDto;
import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;

    public Department getOrCreate(String name) {
        return departmentRepository
                    .findByName(name)
                    .orElseGet(() -> create(Department.builder().name(name).build()));
    }

    public Department create(Department department) {
        return departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll().stream().map(DepartmentDto::of).collect(Collectors.toList());
    }

    public void update(Long id, DepartmentUpdateDto dto) {
        Department department = departmentRepository.findById(id).orElseThrow();
        department.rename(dto.getName());
    }

    @Transactional(readOnly = true)
    public List<Department> search(String name) {
        return departmentRepository.findByNameContaining(name);
    }
}
