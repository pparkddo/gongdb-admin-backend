package com.gongdb.admin.announcement.service;

import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;

    @Transactional
    public Department getOrCreate(String name) {
        return departmentRepository
                    .findByName(name)
                    .orElseGet(() -> create(Department.builder().name(name).build()));
    }

    public Department create(Department department) {
        return departmentRepository.save(department);
    }
}
