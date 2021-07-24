package com.gongdb.admin.announcement.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.request.AnnouncementSequenceInputDto;
import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.entity.Attachment;
import com.gongdb.admin.announcement.entity.Company;
import com.gongdb.admin.announcement.entity.UploadFile;
import com.gongdb.admin.announcement.repository.AnnouncementSequenceRepository;
import com.gongdb.admin.announcement.service.file.FileService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementSequenceService {
    
    private final AnnouncementSequenceRepository announcementSequenceRepository;
    private final CompanyService companyService;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public AnnouncementSequence get(Long id) {
        return announcementSequenceRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public AnnouncementSequence get(Company company, String sequence) {
        return announcementSequenceRepository.findByCompanyAndSequence(company, sequence).orElseThrow();
    }

    public AnnouncementSequence create(AnnouncementSequenceInputDto dto) {
        AnnouncementSequence announcementSequence = AnnouncementSequence.builder()
            .company(companyService.getOrCreate(dto.getCompanyName()))
            .sequence(dto.getSequence())
            .receiptStartTimestamp(dto.getReceiptStartTimestamp())
            .receiptEndTimestamp(dto.getReceiptEndTimestamp())
            .link(dto.getLink())
            .uploadFiles(getUploadFiles(dto.getFiles())).build();
        return create(announcementSequence);
    }

    public AnnouncementSequence create(AnnouncementSequence announcementSequence) {
        return announcementSequenceRepository.save(announcementSequence);
    }

    public void update(Long id, AnnouncementSequenceInputDto dto) {
        AnnouncementSequence sequence = announcementSequenceRepository.findById(id).orElseThrow();
        sequence.updateCompany(companyService.getOrCreate(dto.getCompanyName()));
        sequence.updateSequence(dto.getSequence());
        sequence.updateReceiptStartTimestamp(dto.getReceiptStartTimestamp());
        sequence.updateReceiptEndTimestamp(dto.getReceiptEndTimestamp());
        for (Attachment attachment : sequence.getAttachments()) {
            fileService.delete(attachment.getUploadFile());
        }
        sequence.updateAttachments(getUploadFiles(dto.getFiles()));
    }

    private List<UploadFile> getUploadFiles(List<MultipartFile> files) {
        return files.stream().map(each -> fileService.upload(each)).collect(Collectors.toList());
    }
}
