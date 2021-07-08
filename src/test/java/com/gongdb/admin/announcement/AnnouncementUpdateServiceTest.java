package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.request.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.dto.request.LanguageScoreInputDto;
import com.gongdb.admin.announcement.embeddable.LanguageScore;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.entity.Language;
import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.entity.Subject;
import com.gongdb.admin.announcement.repository.AnnouncementRepository;
import com.gongdb.admin.announcement.repository.CertificateRepository;
import com.gongdb.admin.announcement.repository.CompanyRepository;
import com.gongdb.admin.announcement.repository.DepartmentRepository;
import com.gongdb.admin.announcement.repository.LanguageRepository;
import com.gongdb.admin.announcement.repository.PositionRepository;
import com.gongdb.admin.announcement.repository.SubjectRepository;
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
    
    @Autowired
    private AnnouncementUpdateService announcementUpdateService;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    public void updateTest() {
        Announcement announcement = getAnnouncement();
        announcementRepository.save(announcement);
        AnnouncementInputFormDto dto = 
            AnnouncementInputFormDto.builder()
                .certificates(List.of("modifiedCertificate1", "modifiedCertificates2"))
                .companyName("modifiedCompanyName")
                .departments(List.of("modifiedDepartment1", "modifiedDepartment2"))
                .districtName("modifiedDistrictName")
                .headCount("99")
                .languageScores(getLanguageScoreInputDtoList(
                        List.of("modifiedLanguageScore1", "modifiedLanguageScore2")))
                .link("modifiedLink")
                .notes(List.of("modifiedNote1", "modifiedNote2"))
                .positionName("modifiedPositionName")
                .rank("modifiedRank")
                .receiptTimestamp(LocalDateTime.of(2021, 5, 16, 0, 0))
                .recruitLevel("modifiedRecruitLevel")
                .recruitType("modifiedRecruitType")
                .sequence("modifiedSequence")
                .subjects(List.of("modifiedSubject1", "modifiedSubject2"))
                .workingType("modifiedWorkingType").build();

        announcementUpdateService.update(announcement.getId(), dto);
        announcementRepository.flush();

        assertEquals(
            List.of("modifiedNote1", "modifiedNote2"), getCurrentAnnouncementNotes(announcement));
        assertEquals("modifiedCompanyName", announcement.getCompany().getName());
        assertEquals("99", announcement.getHeadCount());
        assertEquals(LocalDateTime.of(2021, 5, 16, 0, 0), announcement.getReceiptTimestamp());
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

    private Announcement getAnnouncement() {
        Company company = getCompany("company");
        Position position = getPosition("position");
        List<Certificate> certificates = getCertificates(List.of("certificate1", "certificate2"));
        List<Department> departments = getDepartments(List.of("department1", "department2"));
        List<Subject> subjects = getSubjects(List.of("subject1", "subject2"));
        List<Language> languages = getLanguages(List.of("language1", "language2"));
        Announcement announcement = Announcement.builder()
                            .company(company)
                            .position(position)
                            .certificates(certificates)
                            .departments(departments)
                            .subjects(subjects)
                            .languageScores(getLanguageScores(languages))
                            .recruitType("recruitType")
                            .recruitLevel("recruitLevel")
                            .workingType("workingType")
                            .receiptTimestamp(LocalDateTime.of(2021, 4, 22, 0, 0))
                            .sequence("sequence")
                            .link("link")
                            .rank("rank")
                            .districtName("district")
                            .headCount("0")
                            .notes(List.of("note1", "note2")).build();
        companyRepository.save(company);
        positionRepository.save(position);
        certificates.forEach(certificateRepository::save);
        departments.forEach(departmentRepository::save);
        subjects.forEach(subjectRepository::save);
        languages.forEach(languageRepository::save);
        announcementRepository.save(announcement);
        return announcement;
    }

    private Company getCompany(String company) {
        return Company.builder().name(company).build();
    }

    private Position getPosition(String position) {
        return Position.builder().name(position).build();
    }

    private List<Certificate> getCertificates(List<String> certificates) {
        return certificates.stream()
            .map(each -> Certificate.builder().name(each).build()).collect(Collectors.toList());
    }

    private List<Department> getDepartments(List<String> departments) {
        return departments.stream()
            .map(each -> Department.builder().name(each).build()).collect(Collectors.toList());
    }

    private List<Subject> getSubjects(List<String> subjects) {
        return subjects.stream()
            .map(each -> Subject.builder().name(each).build()).collect(Collectors.toList());
    }

    private List<Language> getLanguages(List<String> languages) {
        return languages.stream()
            .map(each -> Language.builder().name(each).build()).collect(Collectors.toList());
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
