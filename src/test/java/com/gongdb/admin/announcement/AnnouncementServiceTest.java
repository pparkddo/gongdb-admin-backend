package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.AnnouncementDto;
import com.gongdb.admin.announcement.embeddable.LanguageScore;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.entity.Language;
import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.entity.Subject;
import com.gongdb.admin.announcement.repository.AnnouncementRepository;
import com.gongdb.admin.announcement.service.AnnouncementService;
import com.gongdb.admin.announcement.service.CertificateService;
import com.gongdb.admin.announcement.service.CompanyService;
import com.gongdb.admin.announcement.service.DepartmentService;
import com.gongdb.admin.announcement.service.LanguageService;
import com.gongdb.admin.announcement.service.PositionService;
import com.gongdb.admin.announcement.service.SubjectService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AnnouncementServiceTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AnnouncementService announcementService;

    private Announcement createAnnouncement() {
        Company company = companyService.getOrCreate("company");
        Position position = positionService.getOrCreate("position");
        List<Certificate> certificates = List.of("certificate1", "certificate2")
                                             .stream()
                                             .map(certificateService::getOrCreate)
                                             .collect(Collectors.toList());
        List <Department> departments = List.of("department1", "department2")
                                            .stream()
                                            .map(departmentService::getOrCreate)
                                            .collect(Collectors.toList());
        List<Subject> subjects = List.of("subject1", "subject2")
                                     .stream()
                                     .map(subjectService::getOrCreate)
                                     .collect(Collectors.toList());
        List<Language> languages = List.of("language1", "language2")
                                       .stream()
                                       .map(languageService::getOrCreate)
                                       .collect(Collectors.toList());
        List<LanguageScore> languageScores = 
            languages.stream()
                     .map(each -> LanguageScore.builder().language(each).score(each + " score").build())
                     .collect(Collectors.toList());
        List<String> notes = List.of("note1", "note2");
        Announcement announcement = Announcement
                                        .builder()
                                        .company(company)
                                        .position(position)
                                        .certificates(certificates)
                                        .departments(departments)
                                        .subjects(subjects)
                                        .languageScores(languageScores)
                                        .recruitType("recruitType")
                                        .recruitLevel("recruitLevel")
                                        .workingType("workingType")
                                        .receiptTimestamp(LocalDateTime.of(LocalDate.of(2021, 4, 22), LocalTime.of(0, 0)))
                                        .sequence("sequence")
                                        .link("link")
                                        .rank("rank")
                                        .districtName("district")
                                        .headCount(0)
                                        .notes(notes)
                                        .build();
        return announcementRepository.save(announcement);
    }

    @Test
    public void getTest() {
        Announcement announcement = createAnnouncement();

        AnnouncementDto announcementDto = announcementService.get(announcement.getId());

        assertEquals(announcement.getCompany().getId(), announcementDto.getCompany().getCompanyId());
        assertEquals(announcement.getAnnouncementNotes().stream().map(each -> each.getNote()).collect(Collectors.toList()),
                     announcementDto.getNotes());
    }

    @Test
    public void getAllTest() {
        Announcement announcement1 = createAnnouncement();
        Announcement announcement2 = createAnnouncement();

        List<AnnouncementDto> announcementDtoList = announcementService.getAll();

        assertEquals(2, announcementDtoList.size());
        assertEquals(List.of(announcement1.getId(), announcement2.getId()),
                     announcementDtoList.stream().map(each -> each.getId()).collect(Collectors.toList()));
    }
}
