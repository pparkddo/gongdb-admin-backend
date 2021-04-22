package com.gongdb.admin.announcement.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    @ManyToOne
    @JoinColumn
    private Company company;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Position position;

    @OneToMany(mappedBy = "announcement")
    private List<AnnouncementCertificate> announcementCertificates = new ArrayList<>();

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

    public void addCertificate(Certificate certificate) {
        this.announcementCertificates.add(
            AnnouncementCertificate
            .builder()
            .announcement(this)
            .certificate(certificate)
            .build()
        );
    }

    public void addCertificates(List<Certificate> certificates) {
        certificates.forEach(each -> addCertificate(each));
    }

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
        addCertificates(certificates);
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
