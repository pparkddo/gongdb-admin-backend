package com.gongdb.admin.announcement.service.file;

import com.gongdb.admin.announcement.entity.UploadFile;
import com.gongdb.admin.announcement.repository.UploadFileRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Profile("test")
@Service
@Transactional
@RequiredArgsConstructor
public class TestFileService implements FileService {

    @Value("${file.path}")
    private String filePath;

    private final UploadFileRepository uploadFileRepository;

    @Override
    public UploadFile upload(MultipartFile file) {
        UploadFile uploadFile = UploadFile.builder()
            .filePath(filePath)
            .fileName(file.getOriginalFilename())
            .originalFileName(file.getOriginalFilename())
            .contentType(file.getContentType()).build();
        return uploadFileRepository.save(uploadFile);
    }

    @Override
    public void delete(UploadFile uploadFile) {
        uploadFileRepository.delete(uploadFile);
    }
}
