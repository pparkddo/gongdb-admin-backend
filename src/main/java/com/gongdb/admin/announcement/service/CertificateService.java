package com.gongdb.admin.announcement.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.CertificateDto;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.repository.CertificateRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateService {
    
    private final CertificateRepository certificateRepository;

    @Transactional
    public Certificate getOrCreate(String name) {
        return certificateRepository
                    .findByName(name)
                    .orElseGet(() -> create(Certificate.builder().name(name).build()));
    }

    public Certificate create(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    public List<CertificateDto> getAll() {
        return certificateRepository.findAll().stream().map(CertificateDto::of).collect(Collectors.toList());
    }
}
