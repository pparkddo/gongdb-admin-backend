package com.gongdb.admin.announcement.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.AnnouncementDto;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.repository.AnnouncementRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {
    
    private final AnnouncementRepository announcementRepository;

    public List<AnnouncementDto> getAll() {
        List<Announcement> announcements = announcementRepository.findAll();
        return announcements.stream().map(AnnouncementDto::of).collect(Collectors.toList());
    }

    public AnnouncementDto get(Long id) {
        Announcement announcement = announcementRepository.findById(id).orElseThrow();
        return AnnouncementDto.of(announcement);
    }
}
