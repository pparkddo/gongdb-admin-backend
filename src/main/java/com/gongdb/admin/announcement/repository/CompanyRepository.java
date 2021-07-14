package com.gongdb.admin.announcement.repository;

import java.util.List;
import java.util.Optional;

import com.gongdb.admin.announcement.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    public Optional<Company> findByName(String name);

    public List<Company> findByNameContaining(String name);

}
