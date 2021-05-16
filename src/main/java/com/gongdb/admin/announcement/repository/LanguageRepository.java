package com.gongdb.admin.announcement.repository;

import java.util.Optional;

import com.gongdb.admin.announcement.entity.Language;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    
    public Optional<Language> findByName(String name);
}
