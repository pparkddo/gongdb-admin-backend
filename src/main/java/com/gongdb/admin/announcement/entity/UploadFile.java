package com.gongdb.admin.announcement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.gongdb.admin.global.entity.BaseCreateAuditEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile extends BaseCreateAuditEntity {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String filePath;

    @Column(unique = true, nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String contentType;

    @Builder
    private UploadFile(String filePath, String fileName, String originalFileName, String contentType) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
    }
}
