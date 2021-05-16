package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gongdb.admin.announcement.entity.Language;
import com.gongdb.admin.announcement.repository.LanguageRepository;
import com.gongdb.admin.announcement.service.LanguageService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
}
