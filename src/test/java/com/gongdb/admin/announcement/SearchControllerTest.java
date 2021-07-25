package com.gongdb.admin.announcement;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Language;
import com.gongdb.admin.announcement.service.CompanyService;
import com.gongdb.admin.announcement.service.LanguageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public class SearchControllerTest {
    
    @Autowired private MockMvc mockMvc;
    @Autowired private CompanyService companyService;
    @Autowired private LanguageService languageService;

    private static final String END_POINT = "/api/search";

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation).operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint())) 
            .build();
    }

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
            .andExpect(jsonPath("$.length()").value("2"))
            .andDo(document(
                "search-search",
                requestParameters(
                    parameterWithName("query").description("질의 내용"),
                    parameterWithName("type")
                        .description("질의 유형(ALL, COMPANY, CERTIFICATE, DEPARTMENT, LANGUAGE, POSITION, SUBJECT)")
                        .optional()
                ),
                responseFields(
                    fieldWithPath("[].id").description("검색 항목 ID"),
                    fieldWithPath("[].value").description("검색 항목 값"),
                    fieldWithPath("[].type")
                        .description("검색 항목 유형(ALL, COMPANY, CERTIFICATE, DEPARTMENT, LANGUAGE, POSITION, SUBJECT)")
                )
            ));
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
