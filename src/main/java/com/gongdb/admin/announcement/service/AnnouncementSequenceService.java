package com.gongdb.admin.announcement.service;

import java.util.Optional;

import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.repository.AnnouncementSequenceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementSequenceService {
    
    private final AnnouncementSequenceRepository announcementSequenceRepository;

    @Transactional(readOnly = true)
    public Optional<AnnouncementSequence> get(Company company, String sequence) {
        return announcementSequenceRepository.findByCompanyAndSequence(company, sequence);
    }

    public AnnouncementSequence create(AnnouncementSequence announcementSequence) {
        return announcementSequenceRepository.save(announcementSequence);
    }
}
