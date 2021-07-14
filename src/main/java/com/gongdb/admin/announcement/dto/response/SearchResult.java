package com.gongdb.admin.announcement.dto.response;

import com.gongdb.admin.announcement.dto.request.SearchType;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.entity.Language;
import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.entity.Subject;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchResult {
    
    private Long id;
    private String value;
    private SearchType type;

    public static SearchResult of(Company company) {
        return new SearchResult(company.getId(), company.getName(), SearchType.COMPANY);
    }

    public static SearchResult of(Certificate certificate) {
        return new SearchResult(certificate.getId(), certificate.getName(), SearchType.CERTIFICATE);
    }

    public static SearchResult of(Department department) {
        return new SearchResult(department.getId(), department.getName(), SearchType.DEPARTMENT);
    }

    public static SearchResult of(Language language) {
        return new SearchResult(language.getId(), language.getName(), SearchType.LANGUAGE);
    }

    public static SearchResult of(Position position) {
        return new SearchResult(position.getId(), position.getName(), SearchType.POSITION);
    }

    public static SearchResult of(Subject subject) {
        return new SearchResult(subject.getId(), subject.getName(), SearchType.SUBJECT);
    }

    @Builder
    private SearchResult(Long id, String value, SearchType type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }
}
