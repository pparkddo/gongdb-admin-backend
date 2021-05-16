package com.gongdb.admin.announcement.repository;

import java.util.Optional;

import com.gongdb.admin.announcement.entity.Department;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    public Optional<Department> findByName(String name);
}
