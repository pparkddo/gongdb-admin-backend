package com.gongdb.admin.announcement.dto.response;

import java.time.LocalDateTime;

import com.gongdb.admin.announcement.entity.AnnouncementSequence;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementSequenceDto {
    
    private Long id;
    private CompanyDto company;
    private String sequence;
    private LocalDateTime receiptStartTimestamp;
    private LocalDateTime receiptEndTimestamp;
    private String link;

    @Builder
    private AnnouncementSequenceDto(Long id, CompanyDto company, String sequence,
            LocalDateTime receiptStartTimestamp, LocalDateTime receiptEndTimestamp, String link) {
        this.id = id;
        this.sequence = sequence;
        this.company = company;
        this.receiptStartTimestamp = receiptStartTimestamp;
        this.receiptEndTimestamp = receiptEndTimestamp;
        this.link = link;
    }

    public static AnnouncementSequenceDto of(AnnouncementSequence announcementSequence) {
        return AnnouncementSequenceDto.builder()
            .id(announcementSequence.getId())
            .company(CompanyDto.of(announcementSequence.getCompany()))
            .sequence(announcementSequence.getSequence())
            .receiptStartTimestamp(announcementSequence.getReceiptStartTimestamp())
            .receiptEndTimestamp(announcementSequence.getReceiptEndTimestamp())
            .link(announcementSequence.getLink())
            .build();
    }
}
