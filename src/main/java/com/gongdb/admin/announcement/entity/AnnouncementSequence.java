package com.gongdb.admin.announcement.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.gongdb.admin.global.entity.BaseCreateAuditEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"company_id", "sequence"}))
public class AnnouncementSequence extends BaseCreateAuditEntity {
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String sequence;

    @Column(nullable = false)
    private LocalDateTime receiptStartTimestamp;

    @Column(nullable = false)
    private LocalDateTime receiptEndTimestamp;

    @Column(nullable = true)
    private String link;

    @OneToMany(mappedBy = "announcementSequence", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    public void updateCompany(Company company) {
        this.company = company;
    }

    public void updateSequence(String sequence) {
        this.sequence = sequence;
    }

    public void updateReceiptStartTimestamp(LocalDateTime receiptStartTimestamp) {
        this.receiptStartTimestamp = receiptStartTimestamp;
    }

    public void updateReceiptEndTimestamp(LocalDateTime receiptEndTimestamp) {
        this.receiptEndTimestamp = receiptEndTimestamp;
    }

    public void updateLink(String link) {
        this.link = link;
    }

    public void addAttachments(List<UploadFile> uploadFiles) {
        for (UploadFile each : uploadFiles) {
            Attachment attachment = Attachment.builder()
                .announcementSequence(this)
                .uploadFile(each).build();
            this.attachments.add(attachment);
        }
    }

    @Builder
    private AnnouncementSequence(Company company, String sequence,
            LocalDateTime receiptStartTimestamp, LocalDateTime receiptEndTimestamp, String link,
            List<UploadFile> uploadFiles) {
        if (receiptStartTimestamp.isAfter(receiptEndTimestamp)) {
            throw new IllegalArgumentException("receiptStartTimestamp should be earlier than receiptEndTimestamp");
        }
        this.company = company;
        this.sequence = sequence;
        this.receiptStartTimestamp = receiptStartTimestamp;
        this.receiptEndTimestamp = receiptEndTimestamp;
        this.link = link;
        addAttachments(uploadFiles);
    }
}

