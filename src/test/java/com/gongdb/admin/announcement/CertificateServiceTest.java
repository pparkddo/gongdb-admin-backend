package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.CertificateDto;
import com.gongdb.admin.announcement.dto.CertificateUpdateDto;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.repository.CertificateRepository;
import com.gongdb.admin.announcement.service.CertificateService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class CertificateServiceTest {
    
    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CertificateRepository certificateRepository;

    @Test
    public void createDuplicateTest() {
        String name = "certificate";
        certificateRepository.saveAndFlush(Certificate.builder().name(name).build());

        assertThrows(
            DataIntegrityViolationException.class,
            () -> certificateRepository.saveAndFlush(Certificate.builder().name(name).build())
        );
    }

    @Test
    public void getOrCreateTest() {
        String alreadyExistsName = "name1";
        certificateService.create(Certificate.builder().name(alreadyExistsName).build());

        certificateService.getOrCreate(alreadyExistsName);
        assertEquals(certificateRepository.count(), 1);

        String newName = "name2";
        certificateService.getOrCreate(newName);
        assertEquals(certificateRepository.count(), 2);

        certificateService.getOrCreate(alreadyExistsName);
        certificateService.getOrCreate(newName);
        assertEquals(certificateRepository.count(), 2);
    }

    @Test
    public void getAllTest() {
        List.of("name1", "name2").stream().forEach(certificateService::getOrCreate);

        List<CertificateDto> certificates = certificateService.getAll();
        assertEquals(certificates.size(), 2);
        assertTrue(certificates.stream().map(each -> each.getName()).collect(Collectors.toList()).contains("name1"));
    }

    @Test
    public void updateTest() {
        String beforeUpdateName = "beforeUpdate";
        Certificate certificate
            = certificateService.create(Certificate.builder().name(beforeUpdateName).build());

        String afterUpdateName = "renamed";
        CertificateUpdateDto dto = CertificateUpdateDto.builder().name(afterUpdateName).build();
        certificateService.update(certificate.getId(), dto);
        certificateRepository.flush();
        
        assertEquals(afterUpdateName, certificate.getName());
    }
}
