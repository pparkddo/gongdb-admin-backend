package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.repository.CompanyRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyRepositoryTests {

    @Autowired
    CompanyRepository repository;

	@Test
    public void createAndReadCompany() {
        Company company = Company.builder().name("Test company").build();
        repository.save(company);
        List<Company> companies = repository.findAll();
        assertEquals(companies.size(), 1);
        assertEquals(companies.get(0).getName(), company.getName());
    }
}
