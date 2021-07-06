package com.gongdb.admin.announcement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.gongdb.admin.global.entity.BaseCreateAuditEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Certificate extends BaseCreateAuditEntity {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Builder
    public Certificate(String name) {
        this.name = name;
    }

    public void rename(String name) {
        this.name = name;
    }
}
