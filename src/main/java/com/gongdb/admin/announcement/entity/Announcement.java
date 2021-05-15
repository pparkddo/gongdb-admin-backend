package com.gongdb.admin.announcement.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.gongdb.admin.announcement.embeddable.LanguageScore;

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

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AnnouncementCertificate> announcementCertificates = new ArrayList<>();

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AnnouncementDepartment> announcementDepartments = new ArrayList<>();

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AnnouncementSubject> announcementSubjects = new ArrayList<>();

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AnnouncementLanguageScore> announcementLanguageScores = new ArrayList<>();

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AnnouncementNote> announcementNotes = new ArrayList<>();

    @NotNull
    private String recruitType;

    @NotNull
    private String recruitLevel;

    @NotNull
    private String workingType;

    @NotNull
    private String districtName;

    @NotNull
    private int headCount;

    private LocalDateTime receiptTimestamp;
    private String sequence;
    private String link;
    private String rank;

    public void addCertificate(Certificate certificate) {
        this.announcementCertificates.add(
            AnnouncementCertificate
            .builder()
            .announcement(this)
            .certificate(certificate)
            .build()
        );
    }

    public void addDepartment(Department department) {
        this.announcementDepartments.add(
            AnnouncementDepartment 
            .builder()
            .announcement(this)
            .department(department)
            .build()
        );
    }

    public void addSubject(Subject subject) {
        this.announcementSubjects.add(
            AnnouncementSubject
            .builder()
            .announcement(this)
            .subject(subject)
            .build()
        );
    }

    public void addLanguageScore(LanguageScore languageScore) {
        this.announcementLanguageScores.add(
            AnnouncementLanguageScore
            .builder()
            .announcement(this)
            .languageScore(languageScore)
            .build()
        );
    }

    public void addNote(String note) {
        this.announcementNotes.add(
            AnnouncementNote
            .builder()
            .announcement(this)
            .note(note)
            .build()
        );
    }

    public void addCertificates(List<Certificate> certificates) {
        certificates.forEach(each -> addCertificate(each));
    }

    public void addDepartments(List<Department> departments) {
        departments.forEach(each -> addDepartment(each));
    }

    public void addSubjects(List<Subject> subjects) {
        subjects.forEach(each -> addSubject(each));
    }

    public void addLanguageScores(List<LanguageScore> languageScores) {
        languageScores.forEach(each -> addLanguageScore(each));
    }

    public void addNotes(List<String> notes) {
        notes.forEach(each -> addNote(each));
    }

    @Builder
    public Announcement(
        Company company,
        Position position,
        List<Certificate> certificates,
        List<Department> departments,
        List<Subject> subjects,
        List<LanguageScore> languageScores,
        String recruitType,
        String recruitLevel,
        String workingType,
        LocalDateTime receiptTimestamp,
        String sequence,
        String link,
        String rank,
        String districtName,
        int headCount,
        List<String> notes
    ) {
        this.company = company;
        this.position = position;
        addCertificates(certificates);
        addDepartments(departments);
        addSubjects(subjects);
        addLanguageScores(languageScores);
        this.recruitType = recruitType;
        this.recruitLevel = recruitLevel;
        this.workingType = workingType;
        this.receiptTimestamp = receiptTimestamp;
        this.sequence = sequence;
        this.link = link;
        this.rank = rank;
        this.districtName = districtName;
        this.headCount = headCount;
        addNotes(notes);
    }
}
