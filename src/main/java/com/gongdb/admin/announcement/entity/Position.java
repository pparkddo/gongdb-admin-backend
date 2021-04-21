package com.gongdb.admin.announcement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Position {
    
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @Builder
    public Position(String name) {
        this.name = name;
    }
}
