package com.gongdb.admin.announcement.dto.response;

import com.gongdb.admin.announcement.entity.Attachment;
import com.gongdb.admin.announcement.entity.UploadFile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFileDto {
    
    private Long id;
    private String fileName;

    @Builder
    private UploadFileDto(Long id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public static UploadFileDto of(Attachment attachment) {
        UploadFile uploadFile = attachment.getUploadFile();
        return of(uploadFile);
    }

    public static UploadFileDto of(UploadFile uploadFile) {
        return UploadFileDto.builder()
            .id(uploadFile.getId())
            .fileName(uploadFile.getOriginalFileName()).build();
    }
}
