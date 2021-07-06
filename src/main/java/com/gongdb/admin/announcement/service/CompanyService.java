package com.gongdb.admin.announcement.service;

import com.gongdb.admin.announcement.dto.CompanyUpdateDto;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.repository.CompanyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    
    private final CompanyRepository companyRepository;

    public Company getOrCreate(String name) {
        return companyRepository 
                    .findByName(name)
                    .orElseGet(() -> create(Company.builder().name(name).build()));
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public void update(Long id, CompanyUpdateDto dto) {
        Company company = companyRepository.findById(id).orElseThrow();
        company.rename(dto.getName());
    }
}
