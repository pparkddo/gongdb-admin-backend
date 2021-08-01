package com.gongdb.admin.announcement.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.request.AnnouncementSequenceInputDto;
import com.gongdb.admin.announcement.dto.response.AnnouncementSequenceDto;
import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.entity.Attachment;
import com.gongdb.admin.announcement.entity.UploadFile;
import com.gongdb.admin.announcement.repository.AnnouncementSequenceRepository;
import com.gongdb.admin.announcement.service.file.FileService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<AnnouncementSequenceDto> getAll(Pageable pageable) {
        Page<AnnouncementSequence> sequences = announcementSequenceRepository.findAll(pageable);
        return sequences.map(AnnouncementSequenceDto::of);
    }

    @Transactional(readOnly = true)
    public AnnouncementSequence get(Long id) {
        return announcementSequenceRepository.findById(id).orElseThrow();
    }

    public AnnouncementSequence create(AnnouncementSequenceInputDto dto) {
        AnnouncementSequence announcementSequence = AnnouncementSequence.builder()
            .company(companyService.getOrCreate(dto.getCompanyName()))
            .sequence(dto.getSequence())
            .receiptStartTimestamp(dto.getReceiptStartTimestamp())
            .receiptEndTimestamp(dto.getReceiptEndTimestamp())
            .link(dto.getLink())
            .uploadFiles(getUploadFiles(dto.getFiles())).build();
        return announcementSequenceRepository.save(announcementSequence);
    }

    public void update(Long id, AnnouncementSequenceInputDto dto) {
        AnnouncementSequence sequence = announcementSequenceRepository.findById(id).orElseThrow();
        sequence.updateCompany(companyService.getOrCreate(dto.getCompanyName()));
        sequence.updateSequence(dto.getSequence());
        sequence.updateReceiptStartTimestamp(dto.getReceiptStartTimestamp());
        sequence.updateReceiptEndTimestamp(dto.getReceiptEndTimestamp());
        sequence.addAttachments(getUploadFiles(dto.getFiles()));
    }

    public void delete(Long id) {
        AnnouncementSequence sequence = announcementSequenceRepository.findById(id).orElseThrow();
        announcementSequenceRepository.delete(sequence);
        for (Attachment attachment : sequence.getAttachments()) {
            fileService.delete(attachment.getUploadFile().getId());
        }
    }
    
    public void deleteAttachment(Long sequenceId, Long fileId) {
        AnnouncementSequence sequence = announcementSequenceRepository.findById(sequenceId).orElseThrow();
        Attachment attachment = sequence.getAttachments().stream()
            .filter(each -> each.getUploadFile().getId() == fileId)
            .findFirst()  // id 이기 때문에 하나 이상 발견될 수 없음
            .orElseThrow();
        UploadFile uploadFile = attachment.getUploadFile();

        sequence.getAttachments().remove(attachment);
        fileService.delete(uploadFile.getId());
    }

    private List<UploadFile> getUploadFiles(List<MultipartFile> files) {
        return files.stream().map(each -> fileService.upload(each)).collect(Collectors.toList());
    }
}
