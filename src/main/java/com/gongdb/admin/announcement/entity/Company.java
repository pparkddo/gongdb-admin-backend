package com.gongdb.admin.announcement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Company {
    
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Builder
    public Company(String name) {
        this.name = name;
    }
}
