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
import com.gongdb.admin.announcement.dto.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.dto.LanguageScoreInputDto;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.service.AnnouncementCreationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AnnouncementControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AnnouncementCreationService announcementCreationService;

    private static final String END_POINT = "/api/announcement";

    @Test
    public void createAnnouncementTest() throws Exception {
        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
                .certificates(List.of("certificate1", "certificates2"))
                .companyName("회사명")
                .departments(List.of("department1", "department2"))
                .districtName("districtName")
                .headCount(0)
                .link("link")
                .notes(List.of("note1", "note2"))
                .positionName("positionName")
                .rank("rank")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 16, 0, 0))
                .recruitLevel("recruitLevel")
                .recruitType("recruitType")
                .sequence("sequence")
                .subjects(List.of("subject1", "subject2"))
                .workingType("workingType").build();
        String content = convertToNonNullValueJsonString(announcementInputFormDto);

        this.mockMvc
            .perform(
                post(END_POINT)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void createBadRequestAnnouncementTest() throws Exception {
        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
                .departments(List.of("department1", "department2"))
                .districtName("districtName")
                .headCount(0)
                .link("link")
                .notes(List.of("note1", "note2"))
                .positionName("positionName")
                .rank("rank")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 16, 0, 0))
                .recruitLevel("recruitLevel")
                .recruitType("recruitType")
                .sequence("sequence")
                .subjects(List.of("subject1", "subject2"))
                .workingType("workingType").build();
        String content = convertToNonNullValueJsonString(announcementInputFormDto);
        
        this.mockMvc
            .perform(
                post(END_POINT)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getRecentAnnouncementTest() throws Exception {
        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
                .certificates(List.of("certificate1", "certificates2"))
                .companyName("companyName")
                .departments(List.of("department1", "department2"))
                .districtName("districtName")
                .headCount(0)
                .languageScores(getLanguageScoreInputDtoList(List.of("languageScore1", "languageScore2")))
                .link("link")
                .notes(List.of("note1", "note2"))
                .positionName("positionName")
                .rank("rank")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 16, 0, 0))
                .recruitLevel("recruitLevel")
                .recruitType("recruitType")
                .sequence("sequence")
                .subjects(List.of("subject1", "subject2"))
                .workingType("workingType").build();
        announcementCreationService.create(announcementInputFormDto);

        this.mockMvc
            .perform(get(END_POINT + "/recent"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.company.companyName").value("companyName"));
    }

    @Test
    public void updateAnnouncementTest() throws Exception {
        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
                .certificates(List.of("certificate1", "certificates2"))
                .companyName("companyName")
                .departments(List.of("department1", "department2"))
                .districtName("districtName")
                .headCount(0)
                .languageScores(getLanguageScoreInputDtoList(List.of("languageScore1", "languageScore2")))
                .link("link")
                .notes(List.of("note1", "note2"))
                .positionName("positionName")
                .rank("rank")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 16, 0, 0))
                .recruitLevel("recruitLevel")
                .recruitType("recruitType")
                .sequence("sequence")
                .subjects(List.of("subject1", "subject2"))
                .workingType("workingType").build();
        Announcement announcement = announcementCreationService.create(announcementInputFormDto);
        String content = convertToNonNullValueJsonString(announcementInputFormDto);

        this.mockMvc
            .perform(
                put(END_POINT + "/" + announcement.getId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void deleteAnnouncementTest() throws Exception {
        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
                .certificates(List.of("certificate1", "certificates2"))
                .companyName("companyName")
                .departments(List.of("department1", "department2"))
                .districtName("districtName")
                .headCount(0)
                .languageScores(getLanguageScoreInputDtoList(List.of("languageScore1", "languageScore2")))
                .link("link")
                .notes(List.of("note1", "note2"))
                .positionName("positionName")
                .rank("rank")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 16, 0, 0))
                .recruitLevel("recruitLevel")
                .recruitType("recruitType")
                .sequence("sequence")
                .subjects(List.of("subject1", "subject2"))
                .workingType("workingType").build();
        Announcement announcement = announcementCreationService.create(announcementInputFormDto);

        this.mockMvc
            .perform(delete(END_POINT + "/" + announcement.getId()))
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
