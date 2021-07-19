package com.gongdb.admin.announcement.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.request.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.dto.request.AnnouncementSequenceInputDto;
import com.gongdb.admin.announcement.embeddable.LanguageScore;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.AnnouncementSequence;
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
    private final AnnouncementSequenceService announcementSequenceService;

    @Transactional
    public void update(Long id, AnnouncementInputFormDto dto) {
        List<Certificate> certificates = getOrCreateCertificates(dto);
        List<Department> departments = getOrCreateDepartments(dto);
        AnnouncementSequence announcementSequence = getOrCreateSequence(dto);
        List<LanguageScore> languageScores = getOrCreateLanguageScores(dto);
        Position position = positionService.getOrCreate(dto.getPositionName());
        List<Subject> subjects = getOrCreateSubjects(dto);
        Announcement announcement = announcementRepository.findById(id).orElseThrow();
        announcement.updateCertificates(certificates);
        announcement.updateDepartments(departments);
        announcement.updateAnnouncementSequence(announcementSequence);
        announcement.updateDistrictName(dto.getDistrictName());
        announcement.updateHeadCount(dto.getHeadCount());
        announcement.updateLanguageScores(languageScores);
        announcement.updateNotes(dto.getNotes());
        announcement.updatePosition(position);
        announcement.updateRank(dto.getRank());
        announcement.updateRecruitLevel(dto.getRecruitLevel());
        announcement.updateRecruitType(dto.getRecruitType());
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
            .map(each -> LanguageScore.builder()
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

    private AnnouncementSequence getOrCreateSequence(AnnouncementInputFormDto dto) {
        AnnouncementSequenceInputDto sequenceDto = dto.getAnnouncementSequence();
        Company company = companyService.getOrCreate(sequenceDto.getCompanyName());
        return announcementSequenceService.get(company, sequenceDto.getSequence())
            .orElseGet(() -> announcementSequenceService.create(
                AnnouncementSequence.builder()
                .company(company)
                .sequence(sequenceDto.getSequence())
                .receiptStartTimestamp(sequenceDto.getReceiptStartTimestamp())
                .receiptEndTimestamp(sequenceDto.getReceiptEndTimestamp())
                .link(sequenceDto.getLink())
                .build())
            );
    }
}
