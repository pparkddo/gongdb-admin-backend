package com.gongdb.admin.announcement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gongdb.admin.announcement.dto.request.CompanyUpdateDto;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.service.CompanyService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class CompanyControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String END_POINT = "/api/company";

    @Test
    public void updateCompanyTest() throws Exception {
        Company company = companyService.getOrCreate("name");

        CompanyUpdateDto dto = CompanyUpdateDto.builder().name("renamed").build();
        String content = convertToNonNullValueJsonString(dto);

        this.mockMvc
            .perform(
                put(END_POINT + "/" + company.getId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            ) 
            .andDo(print())
            .andExpect(status().isOk());
    }

    private String convertToNonNullValueJsonString(Object value) throws JsonProcessingException {
        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                           .writerWithDefaultPrettyPrinter()
                           .writeValueAsString(value);
    }
}
