package com.gongdb.admin.announcement;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gongdb.admin.announcement.dto.request.AnnouncementSequenceInputDto;
import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.service.AnnouncementSequenceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public class AnnouncementSequenceControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AnnouncementSequenceService announcementSequenceService;

    private static final String END_POINT = "/api/sequence";
    
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
    public void createTest() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile(
            "files",
            "first.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "First File!".getBytes());

        MockMultipartFile secondFile = new MockMultipartFile(
            "files",
            "second.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Second File!!".getBytes());

        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link").build();

        String data = convertToNonNullValueJsonString(announcementSequenceInputDto);

        MockMultipartFile content = new MockMultipartFile(
            "content",
            "content",
            MediaType.APPLICATION_JSON_VALUE,
            data.getBytes(StandardCharsets.UTF_8));

        this.mockMvc
            .perform(
                multipart(END_POINT)
                .file(firstFile)
                .file(secondFile)
                .file(content)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "sequence-create",
                requestParts(
                    partWithName("content").description("차수 정보"),
                    partWithName("files").description("업로드 할 파일").optional()
                ),
                requestPartFields(
                    "content",
                    fieldWithPath("companyName").description("회사명"),
                    fieldWithPath("sequence").description("차수명"),
                    fieldWithPath("receiptStartTimestamp").description("접수 시작일"),
                    fieldWithPath("receiptEndTimestamp").description("접수 종료일"),
                    fieldWithPath("link").description("공고 링크").optional()
                ),
                responseFields(
                    fieldWithPath("timestamp").description("응답 시각"),
                    fieldWithPath("message").description("응답 메시지")
                )
            ));
    }

    @Test
    public void updateTest() throws Exception {
        AnnouncementSequenceInputDto dto = AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence = announcementSequenceService.create(dto);

        AnnouncementSequenceInputDto modified = AnnouncementSequenceInputDto.builder()
            .companyName("modifiedCompanyName")
            .sequence("modifiedSequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("modifiedLink").build();
        String data = convertToNonNullValueJsonString(modified);

        MockMultipartFile firstFile = new MockMultipartFile(
            "files",
            "first.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "First File!".getBytes());

        MockMultipartFile secondFile = new MockMultipartFile(
            "files",
            "second.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Second File!!".getBytes());

        MockMultipartFile content = new MockMultipartFile(
            "content",
            "content",
            MediaType.APPLICATION_JSON_VALUE,
            data.getBytes(StandardCharsets.UTF_8));

        this.mockMvc
            .perform(
                getPutMultipartBuilder(END_POINT + "/{id}", sequence.getId())
                .file(firstFile)
                .file(secondFile)
                .file(content)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "sequence-update",
                pathParameters(parameterWithName("id").description("차수 ID")),
                requestParts(
                    partWithName("content").description("차수 정보"),
                    partWithName("files").description("업로드 할 파일").optional()
                ),
                requestPartFields(
                    "content",
                    fieldWithPath("companyName").description("회사명"),
                    fieldWithPath("sequence").description("차수명"),
                    fieldWithPath("receiptStartTimestamp").description("접수 시작일"),
                    fieldWithPath("receiptEndTimestamp").description("접수 종료일"),
                    fieldWithPath("link").description("공고 링크").optional()
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

    private MockMultipartHttpServletRequestBuilder getPutMultipartBuilder(String urlTemplate, Object... urlVariables) {
        MockMultipartHttpServletRequestBuilder builder = RestDocumentationRequestBuilders.fileUpload(urlTemplate, urlVariables);
        builder.with(new RequestPostProcessor(){
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });
        return builder;
    }
}
