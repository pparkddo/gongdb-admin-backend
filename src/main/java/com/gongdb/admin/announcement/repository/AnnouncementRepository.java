package com.gongdb.admin.announcement.repository;

import java.util.Optional;

import com.gongdb.admin.announcement.entity.Announcement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    
    public Optional<Announcement> findFirstByOrderByIdDesc();
}
