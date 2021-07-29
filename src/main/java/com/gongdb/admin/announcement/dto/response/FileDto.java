package com.gongdb.admin.announcement.dto.response;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDto {
    
    private String fileName;
    private MediaType mediaType;
    private Resource resource;

    @Builder
    private FileDto(String fileName, MediaType mediaType, Resource resource) {
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.resource = resource;
    }
}
