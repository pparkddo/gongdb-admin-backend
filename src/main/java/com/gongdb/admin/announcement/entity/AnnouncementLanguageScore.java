package com.gongdb.admin.announcement.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.gongdb.admin.announcement.embeddable.LanguageScore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AnnouncementLanguageScore {
    
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @Embedded
    private LanguageScore languageScore;

    @Builder
    public AnnouncementLanguageScore(Announcement announcement, LanguageScore languageScore) {
        this.announcement = announcement;
        this.languageScore = languageScore;
    }
}
