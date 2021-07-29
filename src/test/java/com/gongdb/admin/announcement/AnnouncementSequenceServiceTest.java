package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import com.gongdb.admin.announcement.dto.request.AnnouncementSequenceInputDto;
import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.service.AnnouncementSequenceService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class AnnouncementSequenceServiceTest {

    @Autowired private AnnouncementSequenceService announcementSequenceService;

    @Test
    public void createTest() {
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());

        AnnouncementSequenceInputDto dto = AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 30, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 30, 0, 0))
            .link("link")
            .files(List.of(file)).build();
        AnnouncementSequence sequence = announcementSequenceService.create(dto);

        assertEquals(1, sequence.getAttachments().size());
        assertEquals("companyName", sequence.getCompany().getName());
    }

    @Test
    public void updateTest() {
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());
        AnnouncementSequenceInputDto dto = AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 30, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 30, 0, 0))
            .link("link")
            .files(List.of(file)).build();
        AnnouncementSequence sequence = announcementSequenceService.create(dto);

        AnnouncementSequenceInputDto modified = AnnouncementSequenceInputDto.builder()
            .companyName("modifiedCompanyName")
            .sequence("modifiedSequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 30, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 30, 0, 0))
            .link("modifiedLink")
            .files(List.of()).build();
        announcementSequenceService.update(sequence.getId(), modified);

        assertEquals("modifiedCompanyName", sequence.getCompany().getName());
        assertEquals(0, sequence.getAttachments().size());
    }
}
