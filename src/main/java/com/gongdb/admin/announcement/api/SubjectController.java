package com.gongdb.admin.announcement.api;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.request.SubjectUpdateDto;
import com.gongdb.admin.announcement.service.SubjectService;
import com.gongdb.admin.global.dto.SimpleMessageResponse;

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
    public SimpleMessageResponse updateSubject(
            @PathVariable Long id, @Valid @RequestBody SubjectUpdateDto subjectUpdateDto) {
        subjectService.update(id, subjectUpdateDto);
        return SimpleMessageResponse.of("정상적으로 수정되었습니다");
    }
}
