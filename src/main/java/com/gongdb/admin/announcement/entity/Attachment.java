package com.gongdb.admin.announcement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.gongdb.admin.global.entity.BaseCreateAuditEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment extends BaseCreateAuditEntity {
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sequence_id", nullable = false)
    private AnnouncementSequence announcementSequence;

    @ManyToOne
    @JoinColumn(name = "attachment_id")
    private UploadFile uploadFile;

    @Builder
    private Attachment(AnnouncementSequence announcementSequence, UploadFile uploadFile) {
        this.announcementSequence = announcementSequence;
        this.uploadFile = uploadFile;
    }
}
