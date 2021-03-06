package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gongdb.admin.announcement.dto.request.LanguageUpdateDto;
import com.gongdb.admin.announcement.entity.Language;
import com.gongdb.admin.announcement.repository.LanguageRepository;
import com.gongdb.admin.announcement.service.LanguageService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class LanguageServiceTest {
    
    @Autowired
    private LanguageService languageService;

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    public void getOrCreateTest() {
        String alreadyExistsName = "name1";
        languageService.create(Language.builder().name(alreadyExistsName).build());

        languageService.getOrCreate(alreadyExistsName);
        assertEquals(languageRepository.count(), 1);

        String newName = "name2";
        languageService.getOrCreate(newName);
        assertEquals(languageRepository.count(), 2);

        languageService.getOrCreate(alreadyExistsName);
        languageService.getOrCreate(newName);
        assertEquals(languageRepository.count(), 2);
    }

    @Test
    public void updateTest() {
        String beforeUpdateName = "beforeUpdate";
        Language language
            = languageService.create(Language.builder().name(beforeUpdateName).build());

        String afterUpdateName = "renamed";
        LanguageUpdateDto dto = LanguageUpdateDto.builder().name(afterUpdateName).build();
        languageService.update(language.getId(), dto);
        languageRepository.flush();
        
        assertEquals(afterUpdateName, language.getName());
    }
}
