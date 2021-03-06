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
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
import com.gongdb.admin.announcement.entity.UploadFile;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
    public void getTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());

        AnnouncementSequenceInputDto dto = AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 31, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 31, 0, 0))
            .link("link")
            .files(List.of(file)).build();
        AnnouncementSequence sequence = announcementSequenceService.create(dto);

        this.mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(
                    END_POINT + "/{id}", sequence.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "sequence-get",
                pathParameters(parameterWithName("id").description("?????? ID")),
                responseFields(
                    fieldWithPath("id").description("?????? ID"),
                    fieldWithPath("company.id").description("?????? ID"),
                    fieldWithPath("company.name").description("?????? ??????"),
                    fieldWithPath("sequence").description("?????? ??????"),
                    fieldWithPath("receiptStartTimestamp").description("?????? ?????????"),
                    fieldWithPath("receiptEndTimestamp").description("?????? ?????????"),
                    fieldWithPath("link").description("?????? ??????"),
                    fieldWithPath("files[]").description("?????? ?????? ??????"),
                    fieldWithPath("files[].id").description("?????? ?????? ?????? ID"),
                    fieldWithPath("files[].fileName").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("createdTimestamp").description("????????? ??????")
                )
            ));
    }

    @Test
    public void getAllTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());

        AnnouncementSequenceInputDto dto = AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 31, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 31, 0, 0))
            .link("link")
            .files(List.of(file)).build();
        announcementSequenceService.create(dto);

        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(END_POINT)
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "sequence-get-page",
                requestParameters(parameterWithName("page").description("????????? ?????????").optional()),
                responseFields(
                    fieldWithPath("content").description("?????? ?????????"),
                    fieldWithPath("content[].id").description("?????? ID"),
                    fieldWithPath("content[].company.id").description("?????? ID"),
                    fieldWithPath("content[].company.name").description("?????? ??????"),
                    fieldWithPath("content[].sequence").description("?????? ??????"),
                    fieldWithPath("content[].receiptStartTimestamp").description("?????? ?????????"),
                    fieldWithPath("content[].receiptEndTimestamp").description("?????? ?????????"),
                    fieldWithPath("content[].link").description("?????? ??????"),
                    fieldWithPath("content[].files[]").description("?????? ?????? ??????"),
                    fieldWithPath("content[].files[].id").description("?????? ?????? ?????? ID"),
                    fieldWithPath("content[].files[].fileName").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("content[].createdTimestamp").description("????????? ??????"),
                    fieldWithPath("pageable").description("????????? ?????? ??????"),
                    fieldWithPath("pageable.sort").description("????????? ?????? ??????"),
                    fieldWithPath("pageable.sort.sorted").description("????????? ?????? ??????"),
                    fieldWithPath("pageable.sort.unsorted").description("????????? ????????? ??????"),
                    fieldWithPath("pageable.sort.empty").description("???????????? ???????????? ??????"),
                    fieldWithPath("pageable.offset").description("????????? ?????????"),
                    fieldWithPath("pageable.pageSize").description("??? ????????? ??? ??????"),
                    fieldWithPath("pageable.pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageable.unpaged").description("????????? ????????? ??????"),
                    fieldWithPath("pageable.paged").description("????????? ?????? ??????"),
                    fieldWithPath("last").description("????????? ????????? ??????"),
                    fieldWithPath("totalElements").description("?????? ???????????? ??????"),
                    fieldWithPath("totalPages").description("??? ????????? ???"),
                    fieldWithPath("size").description("??? ????????? ??? ??????"),
                    fieldWithPath("number").description("?????? ?????????"),
                    fieldWithPath("sort.sorted").description("????????? ?????? ??????"),
                    fieldWithPath("sort.unsorted").description("????????? ????????? ??????"),
                    fieldWithPath("sort.empty").description("???????????? ???????????? ??????"),
                    fieldWithPath("first").description("??? ????????? ??????"),
                    fieldWithPath("numberOfElements").description("?????? ???????????? ??????"),
                    fieldWithPath("empty").description("???????????? ???????????? ??????")
                )
            ));
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
                    partWithName("content").description("?????? ??????"),
                    partWithName("files").description("????????? ??? ??????").optional()
                ),
                requestPartFields(
                    "content",
                    fieldWithPath("companyName").description("?????????"),
                    fieldWithPath("sequence").description("?????????"),
                    fieldWithPath("receiptStartTimestamp").description("?????? ?????????"),
                    fieldWithPath("receiptEndTimestamp").description("?????? ?????????"),
                    fieldWithPath("link").description("?????? ??????").optional()
                ),
                responseFields(
                    fieldWithPath("timestamp").description("?????? ??????"),
                    fieldWithPath("message").description("?????? ?????????")
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
                pathParameters(parameterWithName("id").description("?????? ID")),
                requestParts(
                    partWithName("content").description("?????? ??????"),
                    partWithName("files").description("????????? ??? ??????").optional()
                ),
                requestPartFields(
                    "content",
                    fieldWithPath("companyName").description("?????????"),
                    fieldWithPath("sequence").description("?????????"),
                    fieldWithPath("receiptStartTimestamp").description("?????? ?????????"),
                    fieldWithPath("receiptEndTimestamp").description("?????? ?????????"),
                    fieldWithPath("link").description("?????? ??????").optional()
                ),
                responseFields(
                    fieldWithPath("timestamp").description("?????? ??????"),
                    fieldWithPath("message").description("?????? ?????????")
                )
            ));
    }

    @Test
    public void deleteTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());

        AnnouncementSequenceInputDto dto = AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 31, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 31, 0, 0))
            .link("link")
            .files(List.of(file)).build();
        AnnouncementSequence sequence = announcementSequenceService.create(dto);

        this.mockMvc
            .perform(
                RestDocumentationRequestBuilders.delete(
                    END_POINT + "/{id}", sequence.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "sequence-delete",
                pathParameters(parameterWithName("id").description("?????? ID")),
                responseFields(
                    fieldWithPath("timestamp").description("?????? ??????"),
                    fieldWithPath("message").description("?????? ?????????")
                )
            ));
    }

    @Test
    public void deleteAttachmentTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());

        AnnouncementSequenceInputDto dto = AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 31, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 31, 0, 0))
            .link("link")
            .files(List.of(file)).build();
        AnnouncementSequence sequence = announcementSequenceService.create(dto);

        UploadFile uploadFile = sequence.getAttachments().get(0).getUploadFile();

        this.mockMvc
            .perform(
                RestDocumentationRequestBuilders.delete(
                    END_POINT + "/{sequenceId}/attachment/{fileId}",
                    sequence.getId(),
                    uploadFile.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "sequence-attachment-delete",
                pathParameters(
                    parameterWithName("sequenceId").description("?????? ID"),
                    parameterWithName("fileId").description("?????? ID")
                ),
                responseFields(
                    fieldWithPath("timestamp").description("?????? ??????"),
                    fieldWithPath("message").description("?????? ?????????")
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
