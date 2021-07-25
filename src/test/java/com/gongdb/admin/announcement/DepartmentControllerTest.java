package com.gongdb.admin.announcement;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gongdb.admin.announcement.dto.request.DepartmentUpdateDto;
import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.service.DepartmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
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
public class DepartmentControllerTest {
    
    @Autowired private MockMvc mockMvc;
    @Autowired private DepartmentService departmentService;
    @Autowired private ObjectMapper objectMapper;

    private static final String END_POINT = "/api/department";

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
    public void getEmptyDepartmentsTest() throws Exception {
        this.mockMvc
            .perform(get(END_POINT))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void getCertificatesTest() throws Exception {
        List.of("name1", "name2").stream().forEach(departmentService::getOrCreate);

        this.mockMvc
            .perform(get(END_POINT))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andDo(document(
                "department-get-list",
                responseFields(
                    fieldWithPath("[].id").description("학과 ID"),
                    fieldWithPath("[].name").description("학과 이름")
                )
            ));
    }

    @Test
    public void updateDepartmentTest() throws Exception {
        Department department = departmentService.getOrCreate("name");

        DepartmentUpdateDto dto = DepartmentUpdateDto.builder().name("renamed").build();
        String content = convertToNonNullValueJsonString(dto);

        this.mockMvc
            .perform(
                RestDocumentationRequestBuilders.put(END_POINT + "/{id}", department.getId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            ) 
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "department-update",
                pathParameters(parameterWithName("id").description("학과 ID")),
                requestFields(
                    fieldWithPath("name").description("학과 이름")
                ),
                responseFields(
                    fieldWithPath("timestamp").description("응답 시각"),
                    fieldWithPath("message").description("응답 메시지")
                )
            ));
    }

    private String convertToNonNullValueJsonString(Object value) throws JsonProcessingException {
        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(value);
    }
}
