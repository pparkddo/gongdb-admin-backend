package com.gongdb.admin.announcement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AnnouncementDepartment {
    
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Builder
    public AnnouncementDepartment(Announcement announcement, Department department) {
        this.announcement = announcement;
        this.department = department;
    }
}
