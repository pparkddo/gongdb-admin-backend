package com.gongdb.admin.announcement.api;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.request.LanguageUpdateDto;
import com.gongdb.admin.announcement.service.LanguageService;
import com.gongdb.admin.global.dto.SimpleMessageResponse;

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
    public SimpleMessageResponse updateLanguage(
            @PathVariable Long id, @Valid @RequestBody LanguageUpdateDto languageUpdateDto) {
        languageService.update(id, languageUpdateDto);
        return SimpleMessageResponse.of("정상적으로 수정되었습니다");
    }
}
