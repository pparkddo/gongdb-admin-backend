package com.gongdb.admin.announcement;

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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AnnouncementSequenceControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AnnouncementSequenceService announcementSequenceService;

    private static final String END_POINT = "/api/sequence";

    @Test
    public void createTest() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile(
            "files",
            "first.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "MultipartFile Test".getBytes());

        MockMultipartFile secondFile = new MockMultipartFile(
            "files",
            "second.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "MultipartFile Test".getBytes());

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
            .andExpect(status().isOk());
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

        String data = convertToNonNullValueJsonString(dto);

        MockMultipartFile firstFile = new MockMultipartFile(
            "files",
            "first.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "MultipartFile Test".getBytes());

        MockMultipartFile secondFile = new MockMultipartFile(
            "files",
            "second.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "MultipartFile Test".getBytes());

        MockMultipartFile content = new MockMultipartFile(
            "content",
            "content",
            MediaType.APPLICATION_JSON_VALUE,
            data.getBytes(StandardCharsets.UTF_8));

        this.mockMvc
            .perform(
                getPutMultipartBuilder(END_POINT + "/" + sequence.getId())
                .file(firstFile)
                .file(secondFile)
                .file(content)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    private String convertToNonNullValueJsonString(Object value) throws JsonProcessingException {
        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(value);
    }

    private MockMultipartHttpServletRequestBuilder getPutMultipartBuilder(String uri) {
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(uri);
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
