package com.gongdb.admin.announcement.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.entity.Announcement;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementDto {

    private Long id;
    private Position position;
    private AnnouncementSequenceDto sequence;
    private List<String> certificates = new ArrayList<>();
    private List<String> departments = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    private List<LanguageScore> languageScores = new ArrayList<>();
    private List<String> notes = new ArrayList<>();
    private String recruitType;
    private String recruitLevel;
    private String workingType;
    private String districtName;
    private String headCount;
    private String rank;
    private LocalDateTime createdTimestamp;

    @Builder
    private AnnouncementDto(Long id, Position position, AnnouncementSequenceDto sequence,
            List<String> certificates, List<String> departments, List<Subject> subjects,
            List<LanguageScore> languageScores, String recruitType, String recruitLevel,
            String workingType, String rank, String districtName, String headCount,
            List<String> notes, LocalDateTime createdTimestamp) {
        this.id = id;
        this.position = position;
        this.sequence = sequence;
        this.certificates = certificates;
        this.departments = departments;
        this.subjects = subjects;
        this.languageScores = languageScores;
        this.notes = notes;
        this.recruitType = recruitType;
        this.recruitLevel = recruitLevel;
        this.workingType = workingType;
        this.districtName = districtName;
        this.headCount = headCount;
        this.rank = rank;
        this.createdTimestamp = createdTimestamp;
    }

    public static AnnouncementDto of(Announcement announcement) {
        return AnnouncementDto.builder()
            .id(announcement.getId())
            .position(Position.of(announcement.getPosition()))
            .sequence(AnnouncementSequenceDto.of(announcement.getAnnouncementSequence()))
            .certificates(announcement
                .getAnnouncementCertificates()
                .stream()
                .map(each -> each.getCertificate().getName())
                .collect(Collectors.toList()))
            .departments(announcement
                .getAnnouncementDepartments()
                .stream()
                .map(each -> each.getDepartment().getName())
                .collect(Collectors.toList()))
            .subjects(announcement
                .getAnnouncementSubjects()
                .stream()
                .map(each -> Subject.of(each.getSubject()))
                .collect(Collectors.toList()))
            .languageScores(announcement
                .getAnnouncementLanguageScores()
                .stream()
                .map(each -> LanguageScore.of(each.getLanguageScore()))
                .collect(Collectors.toList()))
            .notes(announcement
                .getAnnouncementNotes()
                .stream()
                .map(each -> each.getNote())
                .collect(Collectors.toList()))
            .recruitType(announcement.getRecruitType())
            .recruitLevel(announcement.getRecruitLevel())
            .workingType(announcement.getWorkingType())
            .districtName(announcement.getDistrictName())
            .headCount(announcement.getHeadCount())
            .rank(announcement.getRank())
            .createdTimestamp(announcement.getCreatedTimestamp())
            .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Position {
        private Long positionId;
        private String positionName;

        @Builder
        private Position(Long positionId, String positionName) {
            this.positionId = positionId;
            this.positionName = positionName;
        }

        public static Position of(com.gongdb.admin.announcement.entity.Position position) {
            return Position.builder().positionId(position.getId()).positionName(position.getName()).build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Subject {
        private Long subjectId;
        private String subjectName;

        @Builder
        private Subject(Long subjectId, String subjectName) {
            this.subjectId = subjectId;
            this.subjectName = subjectName;
        }

        public static Subject of(com.gongdb.admin.announcement.entity.Subject subject) {
            return Subject.builder().subjectId(subject.getId()).subjectName(subject.getName()).build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LanguageScore {
        private Long languageId;
        private String languageName;
        private String score;
        private String perfectScore;

        @Builder
        private LanguageScore(Long languageId, String languageName, String score, String perfectScore) {
            this.languageId = languageId;
            this.languageName = languageName;
            this.score = score;
            this.perfectScore = perfectScore;
        }

        public static LanguageScore of(com.gongdb.admin.announcement.embeddable.LanguageScore languageScore) {
            return LanguageScore.builder()
                 .languageId(languageScore.getLanguage().getId())
                 .languageName(languageScore.getLanguage().getName())
                 .score(languageScore.getScore())
                 .perfectScore(languageScore.getPerfectScore()).build();
        }
    }
}
