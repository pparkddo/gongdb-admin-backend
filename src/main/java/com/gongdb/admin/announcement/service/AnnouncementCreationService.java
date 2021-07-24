package com.gongdb.admin.announcement.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.request.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.embeddable.LanguageScore;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.entity.Certificate;
import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.entity.Subject;
import com.gongdb.admin.announcement.repository.AnnouncementRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementCreationService {
    
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementSequenceService announcementSequenceService;
    private final CertificateService certificateService;
    private final DepartmentService departmentService;
    private final LanguageService languageService;
    private final PositionService positionService;
    private final SubjectService subjectService;

    public Announcement create(Long sequenceId, AnnouncementInputFormDto announcementInputFormDto) {
        AnnouncementSequence sequence = announcementSequenceService.get(sequenceId);
        List<Certificate> certificates = getOrCreateCertificates(announcementInputFormDto);
        List<Department> departments = getOrCreateDepartments(announcementInputFormDto);
        List<LanguageScore> languageScores = getOrCreateLanguageScores(announcementInputFormDto);
        Position position = positionService.getOrCreate(announcementInputFormDto.getPositionName());
        List<Subject> subjects = getOrCreateSubjects(announcementInputFormDto);
        Announcement announcement = Announcement.builder()
            .certificates(certificates)
            .announcementSequence(sequence)
            .departments(departments)
            .districtName(announcementInputFormDto.getDistrictName())
            .headCount(announcementInputFormDto.getHeadCount())
            .languageScores(languageScores)
            .notes(announcementInputFormDto.getNotes())
            .position(position)
            .rank(announcementInputFormDto.getRank())
            .recruitLevel(announcementInputFormDto.getRecruitLevel())
            .recruitType(announcementInputFormDto.getRecruitType())
            .subjects(subjects)
            .workingType(announcementInputFormDto.getWorkingType()).build();
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
            .map(each -> LanguageScore.builder()
                .language(languageService.getOrCreate(each.getName()))
                .score(each.getScore())
                .perfectScore(each.getPerfectScore())
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
