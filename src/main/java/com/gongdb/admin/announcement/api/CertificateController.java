package com.gongdb.admin.announcement.api;

import java.util.List;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.CertificateDto;
import com.gongdb.admin.announcement.dto.CertificateUpdateDto;
import com.gongdb.admin.announcement.service.CertificateService;
import com.gongdb.admin.global.dto.SimpleMessageResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PutMapping("/{id}")
    public SimpleMessageResponse updateCertificate(
            @PathVariable Long id, @Valid @RequestBody CertificateUpdateDto certificateUpdateDto) {
        certificateService.update(id, certificateUpdateDto);
        return SimpleMessageResponse.of("정상적으로 수정되었습니다");
    }
}
