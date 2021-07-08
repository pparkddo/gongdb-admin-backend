package com.gongdb.admin.announcement.api;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.request.CompanyUpdateDto;
import com.gongdb.admin.announcement.service.CompanyService;
import com.gongdb.admin.global.dto.SimpleMessageResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {
    
    private final CompanyService companyService;
    
    @PutMapping("/{id}")
    public SimpleMessageResponse updateCompany(
            @PathVariable Long id, @Valid @RequestBody CompanyUpdateDto companyUpdateDto) {
        companyService.update(id, companyUpdateDto);
        return SimpleMessageResponse.of("정상적으로 수정되었습니다");
    }
}
