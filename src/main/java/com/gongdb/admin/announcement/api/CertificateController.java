package com.gongdb.admin.announcement.api;

import java.util.List;

import com.gongdb.admin.announcement.dto.CertificateDto;
import com.gongdb.admin.announcement.service.CertificateService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certificate")
public class CertificateController {
    
    private final CertificateService certificateService;
    
    @GetMapping
    public List<CertificateDto> getCertificates() {
        return certificateService.getAll();
    }
}
