package com.gongdb.admin.announcement.controller;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.service.AnnouncementCreationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementCreationService announcementCreationService;
    
    @PostMapping
    public Map<String, String> createAnnouncement(@Valid @RequestBody AnnouncementInputFormDto announcementInputFormDto) {
        announcementCreationService.create(announcementInputFormDto);
        return Collections.singletonMap("response", "ok");
    }
}
