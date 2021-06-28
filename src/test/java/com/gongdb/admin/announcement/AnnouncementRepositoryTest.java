package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.embeddable.LanguageScore;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.entity.Language;
import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.entity.Subject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class AnnouncementRepositoryTest {

    @Autowired
    private TestEntityManager em;

    private Announcement announcement;

    @BeforeEach
    public void init() {
        Company company = getCompany("company");
        Position position = getPosition("position");
        List<Certificate> certificates = getCertificates(List.of("certificate1", "certificate2"));
        List<Department> departments = getDepartments(List.of("department1", "department2"));
        List<Subject> subjects = getSubjects(List.of("subject1", "subject2"));
        List<Language> languages = getLanguages(List.of("language1", "language2"));
        announcement = Announcement.builder()
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
        em.persistAndFlush(company);
        em.persistAndFlush(position);
        certificates.forEach(em::persistAndFlush);
        departments.forEach(em::persistAndFlush);
        subjects.forEach(em::persistAndFlush);
        languages.forEach(em::persistAndFlush);
        em.persistAndFlush(announcement);
        em.clear();
    }

	@Test
    public void createAndFindAnnouncement() {
        Announcement a = em.find(Announcement.class, announcement.getId());

        assertEquals("company", a.getCompany().getName());
        assertEquals("position", a.getPosition().getName());
        assertEquals(2, a.getAnnouncementCertificates().size());
        assertEquals(2, a.getAnnouncementDepartments().size());
        assertEquals(2, a.getAnnouncementSubjects().size());
        assertEquals(2, a.getAnnouncementLanguageScores().size());
        assertEquals(2, a.getAnnouncementNotes().size());
        assertEquals(
            List.of("certificate1", "certificate2"),
            a.getAnnouncementCertificates().stream().map(each -> each.getCertificate().getName()).collect(Collectors.toList())
        );
    }

	@Test
    public void orphanRemoveAnnouncementCertificate() {
        Announcement a = em.find(Announcement.class, announcement.getId());

        // announcement 를 삭제함으로써 참조를 잃어버린 AnnouncementCertificate 도 같이 삭제한다 (orphanRemove)
        em.remove(a);
        em.flush();
        em.clear();

        Long count = em.getEntityManager()
                       .createQuery("SELECT COUNT(*) FROM AnnouncementCertificate a", Long.class)
                       .getSingleResult();
        assertEquals(0, count);
    }
    
	@Test
    public void orphanRemoveAnnouncementNote() {
        Announcement a = em.find(Announcement.class, announcement.getId());

        // announcement 를 삭제함으로써 참조를 잃어버린 AnnouncementNote 도 같이 삭제한다 (orphanRemove)
        em.remove(a);
        em.flush();
        em.clear();

        Long count = em.getEntityManager()
                       .createQuery("SELECT COUNT(*) FROM AnnouncementNote a", Long.class)
                       .getSingleResult();
        assertEquals(0, count);
    }

    @Test
    public void updateRankTest() {
        Announcement a = em.find(Announcement.class, announcement.getId());

        a.updateRank("updatedRank");
        em.persistAndFlush(a);

        assertEquals("updatedRank", a.getRank());
    }

    @Test
    public void updateAnnouncementCertificateTest() {
        Announcement a = em.find(Announcement.class, announcement.getId());
        List<Certificate> certificates = getCertificates(List.of("certificate3", "certificate4"));
        certificates.stream().forEach(em::persistAndFlush);

        a.updateCertificates(certificates);
        em.persistAndFlush(a);

        Long count = em.getEntityManager()
                       .createQuery("SELECT COUNT(*) FROM Announcement a", Long.class)
                       .getSingleResult();
        assertEquals(2, a.getAnnouncementCertificates().size());
        assertEquals(
            List.of("certificate3", "certificate4"),
            a.getAnnouncementCertificates()
             .stream()
             .map(each -> each.getCertificate().getName())
             .collect(Collectors.toList()));
        assertEquals(1, count);
    }

    private Company getCompany(String company) {
        return Company.builder().name(company).build();
    }

    private Position getPosition(String position) {
        return Position.builder().name(position).build();
    }

    private List<Certificate> getCertificates(List<String> certificates) {
        return certificates.stream().map(each -> Certificate.builder().name(each).build()).collect(Collectors.toList());
    }

    private List<Department> getDepartments(List<String> departments) {
        return departments.stream().map(each -> Department.builder().name(each).build()).collect(Collectors.toList());
    }

    private List<Subject> getSubjects(List<String> subjects) {
        return subjects.stream().map(each -> Subject.builder().name(each).build()).collect(Collectors.toList());
    }

    private List<Language> getLanguages(List<String> languages) {
        return languages.stream().map(each -> Language.builder().name(each).build()).collect(Collectors.toList());
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
