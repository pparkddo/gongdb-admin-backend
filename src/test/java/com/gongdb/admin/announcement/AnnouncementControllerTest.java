package com.gongdb.admin.announcement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gongdb.admin.announcement.dto.request.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.dto.request.AnnouncementSequenceInputDto;
import com.gongdb.admin.announcement.dto.request.LanguageScoreInputDto;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.service.AnnouncementCreationService;
import com.gongdb.admin.announcement.service.AnnouncementSequenceService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AnnouncementControllerTest {
    
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AnnouncementCreationService announcementCreationService;
    @Autowired private AnnouncementSequenceService announcementSequenceService;

    private static final String ROOT_END_POINT = "/api";

    @Test
    public void createAnnouncementTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();

        String content = convertToNonNullValueJsonString(announcementInputFormDto);

        this.mockMvc
            .perform(
                post(ROOT_END_POINT + "/sequence/" + sequence.getId() + "/announcement")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void createBadRequestAnnouncementTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            // .positionName("positionName")  // suppose bad request
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();

        String content = convertToNonNullValueJsonString(announcementInputFormDto);
        
        this.mockMvc
            .perform(
                post(ROOT_END_POINT + "/sequence/" + sequence.getId() + "/announcement")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getRecentAnnouncementTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();
        announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        this.mockMvc
            .perform(get(ROOT_END_POINT + "/announcement/recent"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sequence.company.name").value("companyName"));
    }

    @Test
    public void updateAnnouncementTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();
        Announcement announcement =
            announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        String content = convertToNonNullValueJsonString(announcementInputFormDto);

        this.mockMvc
            .perform(
                put(ROOT_END_POINT + "/announcement/" + announcement.getId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void deleteAnnouncementTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "files", "test.txt", MediaType.TEXT_PLAIN_VALUE, "test".getBytes());
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of(file)).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();

        Announcement announcement =
            announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        this.mockMvc
            .perform(delete(ROOT_END_POINT + "/announcement/" + announcement.getId()))
            .andDo(print())
            .andExpect(status().isOk());
    }

    private String convertToNonNullValueJsonString(Object value) throws JsonProcessingException {
        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(value);
    }

    private List<LanguageScoreInputDto> getLanguageScoreInputDtoList(List<String> languageScores) {
        return languageScores.stream()
            .map(each -> LanguageScoreInputDto.builder()
                .name(each)
                .score(each + " score")
                .perfectScore(each + "perfectScore")
                .build())
            .collect(Collectors.toList());
    }
}
