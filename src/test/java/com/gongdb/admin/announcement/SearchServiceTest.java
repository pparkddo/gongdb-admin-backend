package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gongdb.admin.announcement.dto.request.SearchType;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.service.CompanyService;
import com.gongdb.admin.announcement.service.PositionService;
import com.gongdb.admin.announcement.service.SearchService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class SearchServiceTest {
    
    @Autowired
    private SearchService searchService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PositionService positionService;

    @Test
    public void searchTest() {
        companyService.create(Company.builder().name("company1").build());
        companyService.create(Company.builder().name("company2").build());
        companyService.create(Company.builder().name("company3").build());
        positionService.create(Position.builder().name("position1").build());
        positionService.create(Position.builder().name("position2").build());

        assertEquals(3, searchService.search("company", SearchType.ALL).size());
        assertEquals(2, searchService.search("1", SearchType.ALL).size());
        assertEquals(1, searchService.search("1", SearchType.COMPANY).size());
    }
}
