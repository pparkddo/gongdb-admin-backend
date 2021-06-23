package com.gongdb.admin.announcement.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.embeddable.LanguageScore;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.entity.Subject;
import com.gongdb.admin.announcement.repository.AnnouncementRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementUpdateService {
    
    private final AnnouncementRepository announcementRepository;
    private final CertificateService certificateService;
    private final CompanyService companyService;
    private final DepartmentService departmentService;
    private final LanguageService languageService;
    private final PositionService positionService;
    private final SubjectService subjectService;

    @Transactional
    public void update(Long id, AnnouncementInputFormDto dto) {
        List<Certificate> certificates = getOrCreateCertificates(dto);
        Company company = companyService.getOrCreate(dto.getCompanyName());
        List<Department> departments = getOrCreateDepartments(dto);
        List<LanguageScore> languageScores = getOrCreateLanguageScores(dto);
        Position position = positionService.getOrCreate(dto.getPositionName());
        List<Subject> subjects = getOrCreateSubjects(dto);
        Announcement announcement = announcementRepository.findById(id).orElseThrow();
        announcement.updateCertificates(certificates);
        announcement.updateCompany(company);
        announcement.updateDepartments(departments);
        announcement.updateDistrictName(dto.getDistrictName());
        announcement.updateHeadCount(dto.getHeadCount());
        announcement.updateLanguageScores(languageScores);
        announcement.updateLink(dto.getLink());
        announcement.updateNotes(dto.getNotes());
        announcement.updatePosition(position);
        announcement.updateRank(dto.getRank());
        announcement.updateReceiptTimestamp(dto.getReceiptTimestamp());
        announcement.updateRecruitLevel(dto.getRecruitLevel());
        announcement.updateRecruitType(dto.getRecruitType());
        announcement.updateSequence(dto.getSequence());
        announcement.updateSubjects(subjects);
        announcement.updateWorkingType(dto.getWorkingType());
    }

    private List<Certificate> getOrCreateCertificates(AnnouncementInputFormDto dto) {
        return dto.getCertificates().stream()
            .map(each -> certificateService.getOrCreate(each))
            .collect(Collectors.toList());
    }

    private List<Department> getOrCreateDepartments(AnnouncementInputFormDto dto) {
        return dto.getDepartments().stream()
            .map(each -> departmentService.getOrCreate(each))
            .collect(Collectors.toList());
    }

    private List<LanguageScore> getOrCreateLanguageScores(AnnouncementInputFormDto dto) {
        return dto.getLanguageScores().stream()
            .map(each -> LanguageScore
                            .builder()
                            .language(languageService.getOrCreate(each.getName()))
                            .score(each.getScore())
                            .perfectScore(each.getPerfectScore())
                            .build())
            .collect(Collectors.toList());
    }
    
    private List<Subject> getOrCreateSubjects(AnnouncementInputFormDto dto) {
        return dto.getSubjects().stream()
            .map(each -> subjectService.getOrCreate(each))
            .collect(Collectors.toList());
    }
}
