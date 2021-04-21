package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

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
        em.persist(certificate);

        Certificate c = em.find(Certificate.class, certificate.getId());

        assertEquals(certificate.getName(), c.getName());
    }
    
	@Test
    public void createAndFindPosition() {
        Position position = Position.builder().name("Test position").build();
        em.persist(position);

        Position p = em.find(Position.class, position.getId());

        assertEquals(position.getName(), p.getName());
    }

	@Test
    public void createAndFindAnnouncement() {
        Company company = Company.builder().name("Test company").build();
        Position position = Position.builder().name("Test position").build();
        Announcement announcement = Announcement.builder()
                                        .company(company)
                                        .position(position)
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
        em.persist(company);
        em.persist(position);
        em.persist(announcement);

        Announcement a = em.find(Announcement.class, announcement.getId());

        assertEquals(company, a.getCompany());
        assertEquals(position, a.getPosition());
    }
}
