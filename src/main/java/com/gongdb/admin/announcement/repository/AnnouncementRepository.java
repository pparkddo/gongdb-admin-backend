package com.gongdb.admin.announcement.repository;

import com.gongdb.admin.announcement.entity.Announcement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    
}
