package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Test
    public void getTest() {
        Announcement announcement = 
            Announcement.builder()
                .company(getOrCreateCompany("company"))
                .position(getOrCreatePosition("position"))
                .certificates(getOrCreateCertificates(List.of("certificate1", "certificate2")))
                .departments(getOrCreateDepartments(List.of("department1", "department2")))
                .subjects(getOrCreateSubjects(List.of("subject1", "subject2")))
                .languageScores(getLanguageScores(getOrCreateLanguages(List.of("language1", "language2"))))
                .recruitType("recruitType")
                .recruitLevel("recruitLevel")
                .workingType("workingType")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 20, 0, 0))
                .sequence("sequence")
                .link("link")
                .rank("rank")
                .districtName("districtName")
                .headCount(0)
                .notes(List.of("note1", "note2")).build();
        announcementRepository.saveAndFlush(announcement);

        AnnouncementDto announcementDto = announcementService.get(announcement.getId());

        assertEquals(announcement.getCompany().getId(), announcementDto.getCompany().getCompanyId());
        assertEquals(announcement.getAnnouncementNotes().stream().map(each -> each.getNote()).collect(Collectors.toList()),
                     announcementDto.getNotes());
    }

    @Test
    public void getAllTest() {
        Announcement announcement1 = 
            Announcement.builder()
                .company(getOrCreateCompany("company"))
                .position(getOrCreatePosition("position"))
                .certificates(getOrCreateCertificates(List.of("certificate1", "certificate2")))
                .departments(getOrCreateDepartments(List.of("department1", "department2")))
                .subjects(getOrCreateSubjects(List.of("subject1", "subject2")))
                .languageScores(getLanguageScores(getOrCreateLanguages(List.of("language1", "language2"))))
                .recruitType("recruitType")
                .recruitLevel("recruitLevel")
                .workingType("workingType")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 20, 0, 0))
                .sequence("sequence")
                .link("link")
                .rank("rank")
                .districtName("districtName")
                .headCount(0)
                .notes(List.of("note1", "note2")).build();
        announcementRepository.saveAndFlush(announcement1);
        Announcement announcement2 = 
            Announcement.builder()
                .company(getOrCreateCompany("company"))
                .position(getOrCreatePosition("position"))
                .certificates(getOrCreateCertificates(List.of("certificate1", "certificate2")))
                .departments(getOrCreateDepartments(List.of("department1", "department2")))
                .subjects(getOrCreateSubjects(List.of("subject1", "subject2")))
                .languageScores(getLanguageScores(getOrCreateLanguages(List.of("language1", "language2"))))
                .recruitType("recruitType")
                .recruitLevel("recruitLevel")
                .workingType("workingType")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 20, 0, 0))
                .sequence("sequence")
                .link("link")
                .rank("rank")
                .districtName("districtName")
                .headCount(0)
                .notes(List.of("note1", "note2")).build();
        announcementRepository.saveAndFlush(announcement2);

        Page<AnnouncementDto> announcementDtoList = announcementService.getAll(PageRequest.of(0, 10));

        assertEquals(2, announcementDtoList.getContent().size());
        assertEquals(List.of(announcement1.getId(), announcement2.getId()),
                     announcementDtoList.stream().map(each -> each.getId()).collect(Collectors.toList()));
    }

    @Test
    public void getRecentAnnouncementTest() {
        assertThrows(NoSuchElementException.class, () -> announcementService.getRecentAnnouncement());

        Announcement announcement = 
            Announcement.builder()
                .company(getOrCreateCompany("company"))
                .position(getOrCreatePosition("position"))
                .certificates(getOrCreateCertificates(List.of("certificate1", "certificate2")))
                .departments(getOrCreateDepartments(List.of("department1", "department2")))
                .subjects(getOrCreateSubjects(List.of("subject1", "subject2")))
                .languageScores(getLanguageScores(getOrCreateLanguages(List.of("language1", "language2"))))
                .recruitType("recruitType")
                .recruitLevel("recruitLevel")
                .workingType("workingType")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 20, 0, 0))
                .sequence("sequence")
                .link("link")
                .rank("rank")
                .districtName("districtName")
                .headCount(0)
                .notes(List.of("note1", "note2")).build();
        announcementRepository.saveAndFlush(announcement);

        AnnouncementDto announcementDto = announcementService.getRecentAnnouncement();
        assertEquals(announcement.getId(), announcementDto.getId());
    }

    @Test
    public void deleteAnnouncementTest() {
        Announcement announcement = 
            Announcement.builder()
                .company(getOrCreateCompany("company"))
                .position(getOrCreatePosition("position"))
                .certificates(getOrCreateCertificates(List.of("certificate1", "certificate2")))
                .departments(getOrCreateDepartments(List.of("department1", "department2")))
                .subjects(getOrCreateSubjects(List.of("subject1", "subject2")))
                .languageScores(getLanguageScores(getOrCreateLanguages(List.of("language1", "language2"))))
                .recruitType("recruitType")
                .recruitLevel("recruitLevel")
                .workingType("workingType")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 20, 0, 0))
                .sequence("sequence")
                .link("link")
                .rank("rank")
                .districtName("districtName")
                .headCount(0)
                .notes(List.of("note1", "note2")).build();
        announcementRepository.saveAndFlush(announcement);

        assertEquals(1, announcementRepository.count());
        announcementService.delete(announcement.getId());
        assertEquals(0, announcementRepository.count());
    }

    private Company getOrCreateCompany(String company) {
        return companyService.getOrCreate(company);
    }

    private Position getOrCreatePosition(String position) {
        return positionService.getOrCreate(position);
    }

    private List<Certificate> getOrCreateCertificates(List<String> certificates) {
        return certificates.stream().map(certificateService::getOrCreate).collect(Collectors.toList());
    }

    private List<Department> getOrCreateDepartments(List<String> departments) {
        return departments.stream().map(departmentService::getOrCreate).collect(Collectors.toList());
    }

    private List<Subject> getOrCreateSubjects(List<String> subjects) {
        return subjects.stream().map(subjectService::getOrCreate).collect(Collectors.toList());
    }

    private List<Language> getOrCreateLanguages(List<String> languages) {
        return languages.stream().map(languageService::getOrCreate).collect(Collectors.toList());
    }

    private List<LanguageScore> getLanguageScores(List<Language> languages) {
        return languages.stream()
                        .map(each -> LanguageScore.builder()
                                                  .language(each)
                                                  .score(each + " score")
                                                  .perfectScore(each + " perfectScore")
                                                  .build())
                        .collect(Collectors.toList());
    }
}
