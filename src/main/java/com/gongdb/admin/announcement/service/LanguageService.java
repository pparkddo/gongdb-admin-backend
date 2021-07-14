package com.gongdb.admin.announcement.service;

import java.util.List;

import com.gongdb.admin.announcement.dto.request.LanguageUpdateDto;
import com.gongdb.admin.announcement.entity.Language;
import com.gongdb.admin.announcement.repository.LanguageRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LanguageService {
    
    private final LanguageRepository languageRepository;

    public Language getOrCreate(String name) {
        return languageRepository 
                    .findByName(name)
                    .orElseGet(() -> create(Language.builder().name(name).build()));
    }

    public Language create(Language language) {
        return languageRepository.save(language);
    }

    public void update(Long id, LanguageUpdateDto dto) {
        Language language = languageRepository.findById(id).orElseThrow();
        language.rename(dto.getName());
    }

    @Transactional(readOnly = true)
    public List<Language> search(String name) {
        return languageRepository.findByNameContaining(name);
    }
}
