package com.gongdb.admin.announcement.repository;

import com.gongdb.admin.announcement.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    
}
