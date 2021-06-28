package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Position;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
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
}
