package com.gongdb.admin.announcement.api;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.AnnouncementDto;
import com.gongdb.admin.announcement.dto.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.service.AnnouncementCreationService;
import com.gongdb.admin.announcement.service.AnnouncementService;
import com.gongdb.admin.announcement.service.AnnouncementUpdateService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementCreationService announcementCreationService;
    private final AnnouncementUpdateService announcementUpdateService;

    @GetMapping
    public Page<AnnouncementDto> getAnnouncement(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
        return announcementService.getAll(pageable);
    }
    
    @GetMapping("/{id}")
    public AnnouncementDto getAnnouncement(@PathVariable Long id) {
        return announcementService.get(id);
    }

    @GetMapping("/recent")
    public AnnouncementDto getRecentAnnouncement() {
        return announcementService.getRecentAnnouncement();
    }

    @PostMapping
    public Map<String, String> createAnnouncement(
        @Valid @RequestBody AnnouncementInputFormDto announcementInputFormDto) {
        announcementCreationService.create(announcementInputFormDto);
        return Collections.singletonMap("response", "ok");
    }

    @PutMapping("/{id}")
    public Map<String, String> updateAnnouncement(
        @PathVariable Long id,
        @Valid @RequestBody AnnouncementInputFormDto announcementInputFormDto) {
        announcementUpdateService.update(id, announcementInputFormDto);
        return Collections.singletonMap("response", "ok");
    }
}
