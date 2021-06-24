package com.gongdb.admin.announcement.service;

import com.gongdb.admin.announcement.dto.AnnouncementDto;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.repository.AnnouncementRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnouncementService {
    
    private final AnnouncementRepository announcementRepository;

    @Transactional(readOnly = true)
    public Page<AnnouncementDto> getAll(Pageable pageable) {
        Page<Announcement> announcements = announcementRepository.findAll(pageable);
        return announcements.map(AnnouncementDto::of);
    }

    @Transactional(readOnly = true)
    public AnnouncementDto get(Long id) {
        Announcement announcement = announcementRepository.findById(id).orElseThrow();
        return AnnouncementDto.of(announcement);
    }

    @Transactional(readOnly = true)
    public AnnouncementDto getRecentAnnouncement() {
        Announcement announcement = announcementRepository.findFirstByOrderByIdDesc().orElseThrow();
        return AnnouncementDto.of(announcement);
    }

    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }
}
