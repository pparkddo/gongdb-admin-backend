package com.gongdb.admin.announcement.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementSequenceInputDto {

    @NotEmpty private String companyName;
    @NotNull private String sequence;
    @NotNull private LocalDateTime receiptStartTimestamp;
    @NotNull private LocalDateTime receiptEndTimestamp;
    private String link;

    @Builder
    private AnnouncementSequenceInputDto(String companyName, LocalDateTime receiptStartTimestamp, 
            LocalDateTime receiptEndTimestamp, String sequence, String link) {
        this.companyName = companyName;
        this.receiptStartTimestamp = receiptStartTimestamp;
        this.receiptEndTimestamp = receiptEndTimestamp;
        this.sequence = sequence;
        this.link = link;
    }
}
