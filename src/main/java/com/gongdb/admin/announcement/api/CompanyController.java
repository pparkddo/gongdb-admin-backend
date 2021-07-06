package com.gongdb.admin.announcement.api;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.CompanyUpdateDto;
import com.gongdb.admin.announcement.service.CompanyService;

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
    public Map<String, String> updateCompany(
            @PathVariable Long id, @Valid @RequestBody CompanyUpdateDto companyUpdateDto) {
        companyService.update(id, companyUpdateDto);
        return Collections.singletonMap("response", "ok");
    }
}
