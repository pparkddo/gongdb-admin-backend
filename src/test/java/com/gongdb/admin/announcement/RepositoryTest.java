package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Position;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class RepositoryTests {

    @Autowired
    private TestEntityManager em;

	@Test
    public void createAndFindCompany() {
        Company company = Company.builder().name("Test company").build();
        em.persist(company);

        Company c = em.find(Company.class, company.getId());

        assertEquals(company.getName(), c.getName());
    }

	@Test
    public void createAndFindCertificate() {
        Certificate certificate = Certificate.builder().name("Test certificate").build();
        em.persistAndFlush(certificate);

        Certificate c = em.find(Certificate.class, certificate.getId());

        assertEquals(certificate.getName(), c.getName());
    }
    
	@Test
    public void createAndFindPosition() {
        Position position = Position.builder().name("Test position").build();
        em.persistAndFlush(position);

        Position p = em.find(Position.class, position.getId());

        assertEquals(position.getName(), p.getName());
    }

	@Test
    public void createAndFindAnnouncement() {
        Company company = Company.builder().name("Test company").build();
        Position position = Position.builder().name("Test position").build();
        List<Certificate> certificates = List.of(
            Certificate.builder().name("certificateA").build(),
            Certificate.builder().name("certificateB").build()
        );
        Announcement announcement = Announcement.builder()
                                        .company(company)
                                        .position(position)
                                        .certificates(certificates)
                                        .recruitType("recruitType")
                                        .recruitLevel("recruitLevel")
                                        .workingType("workingType")
                                        .receiptTimestamp(LocalDate.of(2021, 4, 22))
                                        .sequence("sequence")
                                        .link("link")
                                        .languageScore(0)
                                        .languagePerfectScore(0)
                                        .rank("rank")
                                        .isEither(true)
                                        .build();
        em.persistAndFlush(company);
        em.persistAndFlush(position);
        for (Certificate each : certificates) {
            em.persistAndFlush(each);
        }
        em.persistAndFlush(announcement);
        em.clear();

        Announcement a = em.find(Announcement.class, announcement.getId());

        assertEquals(company.getId(), a.getCompany().getId());
        assertEquals(position.getId(), a.getPosition().getId());
        assertEquals(a.getAnnouncementCertificates().size(), certificates.size());
        assertEquals(
            certificates.stream().map(each -> each.getId()).collect(Collectors.toList()),
            a.getAnnouncementCertificates().stream().map(each -> each.getCertificate().getId()).collect(Collectors.toList())
        );
    }

	@Test
    public void orphanRemoveAnnouncementCertificate() {
        Company company = Company.builder().name("Test company").build();
        Position position = Position.builder().name("Test position").build();
        List<Certificate> certificates = List.of(
            Certificate.builder().name("certificateA").build(),
            Certificate.builder().name("certificateB").build()
        );
        Announcement announcement = Announcement.builder()
                                        .company(company)
                                        .position(position)
                                        .certificates(certificates)
                                        .recruitType("recruitType")
                                        .recruitLevel("recruitLevel")
                                        .workingType("workingType")
                                        .receiptTimestamp(LocalDate.of(2021, 4, 22))
                                        .sequence("sequence")
                                        .link("link")
                                        .languageScore(0)
                                        .languagePerfectScore(0)
                                        .rank("rank")
                                        .isEither(true)
                                        .build();
        em.persistAndFlush(company);
        em.persistAndFlush(position);
        for (Certificate each : certificates) {
            em.persistAndFlush(each);
        }
        em.persistAndFlush(announcement);

        // announcement 를 삭제함으로써 참조를 잃어버린 AnnouncementCertificate 도 같이 삭제한다 (orphanRemove)
        em.remove(announcement);
        em.flush();
        em.clear();

        Long count = em
                        .getEntityManager()
                        .createQuery("SELECT COUNT(*) FROM AnnouncementCertificate a", Long.class)
                        .getSingleResult();
        assertEquals(0, count);
    }
}
