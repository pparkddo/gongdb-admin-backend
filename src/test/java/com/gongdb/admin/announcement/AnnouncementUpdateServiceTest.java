package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.request.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.dto.request.AnnouncementSequenceInputDto;
import com.gongdb.admin.announcement.dto.request.LanguageScoreInputDto;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.service.AnnouncementCreationService;
import com.gongdb.admin.announcement.service.AnnouncementSequenceService;
import com.gongdb.admin.announcement.service.AnnouncementUpdateService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class AnnouncementUpdateServiceTest {

    @Autowired private AnnouncementUpdateService announcementUpdateService;
    @Autowired private AnnouncementCreationService announcementCreationService;
    @Autowired private AnnouncementSequenceService announcementSequenceService;

    @Test
    public void updateTest() {
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
        Announcement saved = announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        AnnouncementInputFormDto dto = AnnouncementInputFormDto.builder()
            .certificates(List.of("modifiedCertificate1", "modifiedCertificates2"))
            .departments(List.of("modifiedDepartment1", "modifiedDepartment2"))
            .districtName("modifiedDistrictName")
            .headCount("99")
            .languageScores(getLanguageScoreInputDtoList(
                    List.of("modifiedLanguageScore1", "modifiedLanguageScore2")))
            .notes(List.of("modifiedNote1", "modifiedNote2"))
            .positionName("modifiedPositionName")
            .rank("modifiedRank")
            .recruitLevel("modifiedRecruitLevel")
            .recruitType("modifiedRecruitType")
            .subjects(List.of("modifiedSubject1", "modifiedSubject2"))
            .workingType("modifiedWorkingType").build();

        announcementUpdateService.update(saved.getId(), dto);

        assertEquals(List.of("modifiedNote1", "modifiedNote2"),
            getCurrentAnnouncementNotes(saved));
        assertEquals("99", saved.getHeadCount());
    }

    private List<String> getCurrentAnnouncementNotes(Announcement announcement) {
        return announcement.getAnnouncementNotes().stream()
            .map(each -> each.getNote()).collect(Collectors.toList());
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
