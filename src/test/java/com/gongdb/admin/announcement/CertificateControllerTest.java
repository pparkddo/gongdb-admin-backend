package com.gongdb.admin.announcement;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gongdb.admin.announcement.dto.CertificateUpdateDto;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.service.CertificateService;

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
public class CertificateControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CertificateService certificateService;

    private static final String END_POINT = "/api/certificate";

    @Test
    public void getEmptyCertificatesTest() throws Exception {
        this.mockMvc
            .perform(get(END_POINT))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void getCertificatesTest() throws Exception {
        List.of("name1", "name2").stream().forEach(certificateService::getOrCreate);

        this.mockMvc
            .perform(get(END_POINT))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void updateCertificateTest() throws Exception {
        Certificate certificate = certificateService.getOrCreate("name");

        CertificateUpdateDto dto = CertificateUpdateDto.builder().name("renamed").build();
        String content = convertToNonNullValueJsonString(dto);

        this.mockMvc
            .perform(
                put(END_POINT + "/" + certificate.getId())
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
