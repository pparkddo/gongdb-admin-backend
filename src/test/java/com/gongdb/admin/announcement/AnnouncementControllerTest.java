package com.gongdb.admin.announcement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gongdb.admin.announcement.dto.AnnouncementInputFormDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AnnouncementControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createAnnouncementTest() throws Exception {
        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto
            .builder()
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
            .workingType("workingType")
            .build();
        String content = 
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(announcementInputFormDto);

        this.mockMvc
            .perform(
                post("/announcement")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void createBadRequestAnnouncementTest() throws Exception {
        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto
            .builder()
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
            .workingType("workingType")
            .build();
        String content = 
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(announcementInputFormDto);
        
        this.mockMvc
            .perform(
                post("/announcement")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
