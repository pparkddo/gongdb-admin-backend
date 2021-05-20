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
public class AnnouncementCreationService {
    
    private final AnnouncementRepository announcementRepository;
    private final CertificateService certificateService;
    private final CompanyService companyService;
    private final DepartmentService departmentService;
    private final LanguageService languageService;
    private final PositionService positionService;
    private final SubjectService subjectService;

    @Transactional
    public Announcement create(AnnouncementInputFormDto announcementInputFormDto) {
        List<Certificate> certificates = getOrCreateCertificates(announcementInputFormDto);
        Company company = companyService.getOrCreate(announcementInputFormDto.getCompanyName());
        List<Department> departments = getOrCreateDepartments(announcementInputFormDto);
        List<LanguageScore> languageScores = getOrCreateLanguageScores(announcementInputFormDto);
        Position position = positionService.getOrCreate(announcementInputFormDto.getPositionName());
        List<Subject> subjects = getOrCreateSubjects(announcementInputFormDto);
        Announcement announcement = 
            Announcement.builder()
                        .certificates(certificates)
                        .company(company)
                        .departments(departments)
                        .districtName(announcementInputFormDto.getDistrictName())
                        .headCount(announcementInputFormDto.getHeadCount())
                        .languageScores(languageScores)
                        .link(announcementInputFormDto.getLink())
                        .notes(announcementInputFormDto.getNotes())
                        .position(position)
                        .rank(announcementInputFormDto.getRank())
                        .receiptTimestamp(announcementInputFormDto.getReceiptTimestamp())
                        .recruitLevel(announcementInputFormDto.getRecruitLevel())
                        .recruitType(announcementInputFormDto.getRecruitType())
                        .sequence(announcementInputFormDto.getSequence())
                        .subjects(subjects)
                        .workingType(announcementInputFormDto.getWorkingType())
                        .build();
        return announcementRepository.save(announcement);
    }

    private List<Certificate> getOrCreateCertificates(AnnouncementInputFormDto announcementInputFormDto) {
        return announcementInputFormDto
            .getCertificates()
            .stream()
            .map(each -> certificateService.getOrCreate(each))
            .collect(Collectors.toList());
    }

    private List<Department> getOrCreateDepartments(AnnouncementInputFormDto announcementInputFormDto) {
        return announcementInputFormDto
            .getDepartments()
            .stream()
            .map(each -> departmentService.getOrCreate(each))
            .collect(Collectors.toList());
    }

    private List<LanguageScore> getOrCreateLanguageScores(AnnouncementInputFormDto announcementInputFormDto) {
        return announcementInputFormDto
            .getLanguageScores()
            .stream()
            .map(each -> LanguageScore
                            .builder()
                            .language(languageService.getOrCreate(each.getName()))
                            .score(each.getScore())
                            .build())
            .collect(Collectors.toList());
    }
    
    private List<Subject> getOrCreateSubjects(AnnouncementInputFormDto announcementInputFormDto) {
        return announcementInputFormDto
            .getSubjects()
            .stream()
            .map(each -> subjectService.getOrCreate(each))
            .collect(Collectors.toList());
    }
}
