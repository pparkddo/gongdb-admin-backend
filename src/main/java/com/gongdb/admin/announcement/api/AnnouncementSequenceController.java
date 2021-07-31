package com.gongdb.admin.announcement.api;

import java.util.List;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.request.AnnouncementSequenceInputDto;
import com.gongdb.admin.announcement.dto.response.AnnouncementSequenceDto;
import com.gongdb.admin.announcement.service.AnnouncementSequenceService;
import com.gongdb.admin.global.dto.SimpleMessageResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sequence")
public class AnnouncementSequenceController {

    private final AnnouncementSequenceService announcementSequenceService;

    @GetMapping
    public Page<AnnouncementSequenceDto> getAnnouncementSequences(
            @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
        return announcementSequenceService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public AnnouncementSequenceDto get(@PathVariable Long id) {
        return AnnouncementSequenceDto.of(announcementSequenceService.get(id));
    }

    @PostMapping
    public SimpleMessageResponse createAnnouncementSequence(
            @Valid @RequestPart(value = "content") AnnouncementSequenceInputDto content,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        if (!CollectionUtils.isEmpty(files)) {
            content.setFiles(files);
        }
        announcementSequenceService.create(content);
        return SimpleMessageResponse.of("정상적으로 생성되었습니다");
    }

    @PutMapping("/{id}")
    public SimpleMessageResponse updateAnnouncementSequence(
            @PathVariable Long id,
            @Valid @RequestPart(value = "content") AnnouncementSequenceInputDto content,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        if (!CollectionUtils.isEmpty(files)) {
            content.setFiles(files);
        }
        announcementSequenceService.update(id, content);
        return SimpleMessageResponse.of("정상적으로 수정되었습니다");
    }

    @DeleteMapping("/{id}")
    public SimpleMessageResponse deleteAnnouncementSequence(@PathVariable Long id) {
        announcementSequenceService.delete(id);
        return SimpleMessageResponse.of("정상적으로 삭제되었습니다");
    }
}
