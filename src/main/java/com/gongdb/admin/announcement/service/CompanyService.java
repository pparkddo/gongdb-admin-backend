package com.gongdb.admin.announcement.service;

import java.util.List;

import com.gongdb.admin.announcement.dto.request.CompanyUpdateDto;
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

    @Transactional(readOnly = true)
    public List<Company> search(String name) {
        return companyRepository.findByNameContaining(name);
    }
}
