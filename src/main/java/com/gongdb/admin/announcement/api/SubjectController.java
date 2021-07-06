package com.gongdb.admin.announcement.api;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.SubjectUpdateDto;
import com.gongdb.admin.announcement.service.SubjectService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subject")
public class SubjectController {
    
    private final SubjectService subjectService;
    
    @PutMapping("/{id}")
    public Map<String, String> updateSubject(
            @PathVariable Long id, @Valid @RequestBody SubjectUpdateDto subjectUpdateDto) {
        subjectService.update(id, subjectUpdateDto);
        return Collections.singletonMap("response", "ok");
    }
}
