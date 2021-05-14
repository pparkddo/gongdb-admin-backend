package com.gongdb.admin.announcement.repository;

import com.gongdb.admin.announcement.entity.Language;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    
}
