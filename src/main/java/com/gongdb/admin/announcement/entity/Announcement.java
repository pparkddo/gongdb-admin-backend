package com.gongdb.admin.announcement.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Announcement {
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private Company company;

    @ManyToOne
    @JoinColumn
    private Position position;

    @OneToMany(mappedBy = "announcement")
    private List<AnnouncementCertificate> announcementCertificates;

    @NotNull
    private String recruitType;

    @NotNull
    private String recruitLevel;

    @NotNull
    private String workingType;

    private LocalDate receiptTimestamp;
    private String sequence;
    private String link;
    private Integer languageScore;
    private Integer languagePerfectScore;
    private String rank;
    private Boolean isEither;

    @Builder
    public Announcement(
        Company company,
        Position position,
        List<Certificate> certificates,
        String recruitType,
        String recruitLevel,
        String workingType,
        LocalDate receiptTimestamp,
        String sequence,
        String link,
        Integer languageScore,
        Integer languagePerfectScore,
        String rank,
        Boolean isEither
    ) {
        this.company = company;
        this.position = position;
        this.announcementCertificates = certificates
                                            .stream()
                                            .map(
                                                each -> AnnouncementCertificate
                                                            .builder()
                                                            .announcement(this)
                                                            .certificate(each)
                                                            .build()
                                            )
                                            .collect(Collectors.toList());
        this.recruitType = recruitType;
        this.recruitLevel = recruitLevel;
        this.workingType = workingType;
        this.receiptTimestamp = receiptTimestamp;
        this.sequence = sequence;
        this.link = link;
        this.languageScore = languageScore;
        this.languagePerfectScore = languagePerfectScore;
        this.rank = rank;
        this.isEither = isEither;
    }
}
