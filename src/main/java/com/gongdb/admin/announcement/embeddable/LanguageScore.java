package com.gongdb.admin.announcement.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.gongdb.admin.announcement.entity.Language;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LanguageScore {
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(nullable = false)
    private String score;

    private String perfectScore;

    @Builder
    public LanguageScore(Language language, String score, String perfectScore) {
        this.language = language;
        this.score = score;
        this.perfectScore = perfectScore;
    }
}
