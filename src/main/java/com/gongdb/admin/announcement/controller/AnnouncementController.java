package com.gongdb.admin.announcement.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.AnnouncementDto;
import com.gongdb.admin.announcement.dto.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.service.AnnouncementCreationService;
import com.gongdb.admin.announcement.service.AnnouncementService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementCreationService announcementCreationService;

    @GetMapping
    public List<AnnouncementDto> getAnnouncement() {
        return announcementService.getAll();
    }
    
    @GetMapping("/{id}")
    public AnnouncementDto getAnnouncement(@PathVariable Long id) {
        return announcementService.get(id);
    }

    @PostMapping
    public Map<String, String> createAnnouncement(@Valid @RequestBody AnnouncementInputFormDto announcementInputFormDto) {
        announcementCreationService.create(announcementInputFormDto);
        return Collections.singletonMap("response", "ok");
    }
}
