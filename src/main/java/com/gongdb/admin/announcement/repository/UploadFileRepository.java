package com.gongdb.admin.announcement.repository;

import com.gongdb.admin.announcement.entity.UploadFile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    
}
