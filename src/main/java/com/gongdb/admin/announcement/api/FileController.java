package com.gongdb.admin.announcement.api;

import com.gongdb.admin.announcement.dto.response.FileDto;
import com.gongdb.admin.announcement.service.file.FileService;
import com.gongdb.admin.global.dto.SimpleMessageResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    
    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        FileDto fileDto = fileService.download(id);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, getContentDisposition(fileDto.getFileName()))
            .contentType(fileDto.getMediaType())
            .body(fileDto.getResource());
    }

    @DeleteMapping("/{id}")
    public SimpleMessageResponse delete(@PathVariable Long id) {
        fileService.delete(id);
        return SimpleMessageResponse.of("정상적으로 삭제되었습니다");
    }

    private String getContentDisposition(String fileName) {
        return "attachment; filename=\"" + fileName + "\"";
    }
}
