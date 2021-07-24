package com.gongdb.admin.announcement.dto.request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementSequenceInputDto {

    @NotEmpty private String companyName;
    @NotNull private String sequence;
    @NotNull private LocalDateTime receiptStartTimestamp;
    @NotNull private LocalDateTime receiptEndTimestamp;
    @Setter private List<MultipartFile> files = new ArrayList<>();
    private String link;

    @Builder
    private AnnouncementSequenceInputDto(String companyName, LocalDateTime receiptStartTimestamp, 
            LocalDateTime receiptEndTimestamp, String sequence, String link,
            List<MultipartFile> files) {
        this.companyName = companyName;
        this.receiptStartTimestamp = receiptStartTimestamp;
        this.receiptEndTimestamp = receiptEndTimestamp;
        this.sequence = sequence;
        this.link = link;
        this.files = files;
    }
}
