package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gongdb.admin.announcement.repository.CertificateRepository;
import com.gongdb.admin.announcement.service.CertificateService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CertificateServiceTest {
    
    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CertificateRepository certificateRepository;

    @Test
    public void getOrCreateTest() {
        String alreadyExistsName = "name1";
        certificateService.create(alreadyExistsName);

        certificateService.getOrCreate(alreadyExistsName);
        assertEquals(certificateRepository.count(), 1);

        String newName = "name2";
        certificateService.getOrCreate(newName);
        assertEquals(certificateRepository.count(), 2);

        certificateService.getOrCreate(alreadyExistsName);
        certificateService.getOrCreate(newName);
        assertEquals(certificateRepository.count(), 2);
    }
}
