package com.gongdb.admin.announcement.embeddable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.gongdb.admin.announcement.entity.Language;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LanguageScore {
    
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id")
    private Language language;

    @NotNull
    private String score;

    private String perfectScore;

    @Builder
    public LanguageScore(Language language, String score, String perfectScore) {
        this.language = language;
        this.score = score;
        this.perfectScore = perfectScore;
    }
}
