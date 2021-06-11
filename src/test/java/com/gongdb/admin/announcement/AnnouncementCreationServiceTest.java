package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.dto.LanguageScoreInputDto;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.service.AnnouncementCreationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AnnouncementCreationServiceTest {
    
    @Autowired
    private AnnouncementCreationService announcementCreationService;

    @Test
    public void createTest() {
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

        assertNotNull(announcement);
        assertEquals(announcement.getAnnouncementLanguageScores().size(), announcementInputFormDto.getLanguageScores().size());
        assertEquals(announcement.getCompany().getName(), announcementInputFormDto.getCompanyName());
        assertEquals(announcement.getHeadCount(), announcementInputFormDto.getHeadCount());
        assertEquals(announcement.getReceiptTimestamp(), announcementInputFormDto.getReceiptTimestamp());
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
