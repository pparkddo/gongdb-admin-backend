package com.gongdb.admin.announcement.api;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.LanguageUpdateDto;
import com.gongdb.admin.announcement.service.LanguageService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/language")
public class LanguageController {
    
    private final LanguageService languageService;
    
    @PutMapping("/{id}")
    public Map<String, String> updateLanguage(
            @PathVariable Long id, @Valid @RequestBody LanguageUpdateDto languageUpdateDto) {
        languageService.update(id, languageUpdateDto);
        return Collections.singletonMap("response", "ok");
    }
}
