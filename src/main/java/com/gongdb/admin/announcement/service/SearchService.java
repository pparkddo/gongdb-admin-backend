package com.gongdb.admin.announcement.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gongdb.admin.announcement.dto.request.SearchType;
import com.gongdb.admin.announcement.dto.response.SearchResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final CompanyService companyService;
    private final CertificateService certificateService;
    private final DepartmentService departmentService;
    private final LanguageService languageService;
    private final PositionService positionService;
    private final SubjectService subjectService;
    
    public List<SearchResult> search(String query, SearchType type) {
        if (type == SearchType.COMPANY) {
            return companyService.search(query)
                .stream().map(SearchResult::of).collect(Collectors.toList());
        }
        if (type == SearchType.CERTIFICATE) {
            return certificateService.search(query)
                .stream().map(SearchResult::of).collect(Collectors.toList());
        }
        if (type == SearchType.DEPARTMENT) {
            return departmentService.search(query)
                .stream().map(SearchResult::of).collect(Collectors.toList());
        }
        if (type == SearchType.LANGUAGE) {
            return languageService.search(query)
                .stream().map(SearchResult::of).collect(Collectors.toList());
        }
        if (type == SearchType.POSITION) {
            return positionService.search(query)
                .stream().map(SearchResult::of).collect(Collectors.toList());
        }
        if (type == SearchType.SUBJECT) {
            return subjectService.search(query)
                .stream().map(SearchResult::of).collect(Collectors.toList());
        }

        Stream<SearchResult> results = Stream.of(
            companyService.search(query).stream().map(SearchResult::of),
            certificateService.search(query).stream().map(SearchResult::of),
            departmentService.search(query).stream().map(SearchResult::of),
            languageService.search(query).stream().map(SearchResult::of),
            positionService.search(query).stream().map(SearchResult::of),
            subjectService.search(query).stream().map(SearchResult::of)
        ).reduce(Stream::concat).orElseGet(Stream::empty);

        return results.collect(Collectors.toList());
    }
}
