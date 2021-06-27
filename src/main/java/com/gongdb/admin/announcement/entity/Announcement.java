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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private String headCount;

    private LocalDateTime receiptTimestamp;
    private String sequence;
    private String link;
    private String rank;

    public void updateCompany(Company company) {
        this.company = company;
    }

    public void updatePosition(Position position) {
        this.position = position;
    }

    public void updateRecruitType(String recruitType) {
        this.recruitType = recruitType;
    }

    public void updateRecruitLevel(String recruitLevel) {
        this.recruitLevel = recruitLevel;
    }

    public void updateWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public void updateDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public void updateHeadCount(String headCount) {
        this.headCount = headCount;
    }

    public void updateReceiptTimestamp(LocalDateTime receiptDateTime) {
        this.receiptTimestamp = receiptDateTime;
    }

    public void updateSequence(String sequence) {
        this.sequence = sequence;
    }

    public void updateLink(String link) {
        this.link = link;
    }

    public void updateRank(String rank) {
        this.rank = rank;
    }

    public void updateCertificates(List<Certificate> certificates) {
        announcementCertificates.clear();
        addCertificates(certificates);
    }

    public void updateDepartments(List<Department> departments) {
        announcementDepartments.clear();
        addDepartments(departments);
    }

    public void updateSubjects(List<Subject> subjects) {
        announcementSubjects.clear();
        addSubjects(subjects);
    }

    public void updateLanguageScores(List<LanguageScore> languageScores) {
        announcementLanguageScores.clear();
        addLanguageScores(languageScores);
    }

    public void updateNotes(List<String> notes) {
        announcementNotes.clear();
        addNotes(notes);
    }

    private void addCertificate(Certificate certificate) {
        this.announcementCertificates.add(
            AnnouncementCertificate
            .builder()
            .announcement(this)
            .certificate(certificate)
            .build()
        );
    }

    private void addDepartment(Department department) {
        this.announcementDepartments.add(
            AnnouncementDepartment 
            .builder()
            .announcement(this)
            .department(department)
            .build()
        );
    }

    private void addSubject(Subject subject) {
        this.announcementSubjects.add(
            AnnouncementSubject
            .builder()
            .announcement(this)
            .subject(subject)
            .build()
        );
    }

    private void addLanguageScore(LanguageScore languageScore) {
        this.announcementLanguageScores.add(
            AnnouncementLanguageScore
            .builder()
            .announcement(this)
            .languageScore(languageScore)
            .build()
        );
    }

    private void addNote(String note) {
        this.announcementNotes.add(
            AnnouncementNote
            .builder()
            .announcement(this)
            .note(note)
            .build()
        );
    }

    private void addCertificates(List<Certificate> certificates) {
        certificates.forEach(each -> addCertificate(each));
    }

    private void addDepartments(List<Department> departments) {
        departments.forEach(each -> addDepartment(each));
    }

    private void addSubjects(List<Subject> subjects) {
        subjects.forEach(each -> addSubject(each));
    }

    private void addLanguageScores(List<LanguageScore> languageScores) {
        languageScores.forEach(each -> addLanguageScore(each));
    }

    private void addNotes(List<String> notes) {
        notes.forEach(each -> addNote(each));
    }

    @Builder
    private Announcement(Company company, Position position, List<Certificate> certificates,
                        List<Department> departments, List<Subject> subjects,
                        List<LanguageScore> languageScores, String recruitType,
                        String recruitLevel, String workingType, LocalDateTime receiptTimestamp,
                        String sequence, String link, String rank, String districtName,
                        String headCount, List<String> notes) {
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
