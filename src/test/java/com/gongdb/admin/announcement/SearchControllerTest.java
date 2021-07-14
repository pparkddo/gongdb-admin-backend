package com.gongdb.admin.announcement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Language;
import com.gongdb.admin.announcement.service.CompanyService;
import com.gongdb.admin.announcement.service.LanguageService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class SearchControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LanguageService languageService;

    private static final String END_POINT = "/api/search";

    @Test
    public void basicSearchTest() throws Exception {
        companyService.create(Company.builder().name("company1").build());
        companyService.create(Company.builder().name("company2").build());
        languageService.create(Language.builder().name("language1").build());
        this.mockMvc
            .perform(
                get(END_POINT)
                .param("query", "1")
                .param("type", "ALL"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value("2"));
    }

    @Test
    public void typeSearchTest() throws Exception {
        companyService.create(Company.builder().name("company1").build());
        companyService.create(Company.builder().name("company2").build());
        languageService.create(Language.builder().name("language1").build());
        this.mockMvc
            .perform(
                get(END_POINT)
                .param("query", "1")
                .param("type", "COMPANY"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value("1"))
            .andExpect(jsonPath("$[0].value").value("company1"));
    }

    @Test
    public void lowerCaseSearchTypeTest() throws Exception {
        this.mockMvc
            .perform(
                get(END_POINT)
                .param("query", "hi")
                .param("type", "All"))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
