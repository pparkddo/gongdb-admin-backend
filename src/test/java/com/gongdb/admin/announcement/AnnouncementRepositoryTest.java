package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class AnnouncementRepositoryTest {

    @Autowired
    private TestEntityManager em;

    private Company company;
    private Position position;
    private List<Certificate> certificates;
    private Announcement announcement;

    @BeforeEach
    public void init() {
        company = Company.builder().name("Test company").build();
        position = Position.builder().name("Test position").build();
        certificates = List.of(
            Certificate.builder().name("certificateA").build(),
            Certificate.builder().name("certificateB").build()
        );
        announcement = Announcement.builder()
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
    }

	@Test
    public void createAndFindAnnouncement() {
        Announcement a = em.find(Announcement.class, announcement.getId());

        assertEquals(company.getName(), a.getCompany().getName());
        assertEquals(position.getName(), a.getPosition().getName());
        assertEquals(a.getAnnouncementCertificates().size(), certificates.size());
        assertEquals(
            certificates.stream().map(each -> each.getId()).collect(Collectors.toList()),
            a.getAnnouncementCertificates().stream().map(each -> each.getCertificate().getId()).collect(Collectors.toList())
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
}
