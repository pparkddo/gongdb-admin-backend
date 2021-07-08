package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gongdb.admin.announcement.dto.request.CompanyUpdateDto;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.repository.CompanyRepository;
import com.gongdb.admin.announcement.service.CompanyService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class CompanyServiceTest {
    
    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void getOrCreateTest() {
        String alreadyExistsName = "name1";
        companyService.create(Company.builder().name(alreadyExistsName).build());

        companyService.getOrCreate(alreadyExistsName);
        assertEquals(companyRepository.count(), 1);

        String newName = "name2";
        companyService.getOrCreate(newName);
        assertEquals(companyRepository.count(), 2);

        companyService.getOrCreate(alreadyExistsName);
        companyService.getOrCreate(newName);
        assertEquals(companyRepository.count(), 2);
    }

    @Test
    public void updateTest() {
        String beforeUpdateName = "beforeUpdate";
        Company company
            = companyService.create(Company.builder().name(beforeUpdateName).build());

        String afterUpdateName = "renamed";
        CompanyUpdateDto dto = CompanyUpdateDto.builder().name(afterUpdateName).build();
        companyService.update(company.getId(), dto);
        companyRepository.flush();
        
        assertEquals(afterUpdateName, company.getName());
    }
}
