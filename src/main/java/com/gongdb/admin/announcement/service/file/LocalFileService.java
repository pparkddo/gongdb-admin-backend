package com.gongdb.admin.announcement.service.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import com.gongdb.admin.announcement.entity.UploadFile;
import com.gongdb.admin.announcement.repository.UploadFileRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Profile("local")
@Service
@Transactional
@RequiredArgsConstructor
public class LocalFileService implements FileService {

    @Value("${file.path}")
    private String filePath;

    private final UploadFileRepository uploadFileRepository;

    @Override
    public UploadFile upload(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        UploadFile uploadFile = UploadFile.builder()
            .filePath(filePath)
            .fileName(fileName)
            .originalFileName(file.getOriginalFilename())
            .contentType(file.getContentType()).build();
        uploadFileRepository.save(uploadFile);
        try {
            file.transferTo(Paths.get(filePath, fileName));
        } catch (IllegalStateException | IOException e) {
            throw new FileException("파일을 생성 중 오류가 발생했습니다");
        }
        return uploadFile;
    }

    @Override
    public void delete(UploadFile uploadFile) {
        uploadFileRepository.delete(uploadFile);
        try {
            Files.delete(Paths.get(uploadFile.getFilePath(), uploadFile.getFileName()));
        } catch (IOException e) {
            throw new FileException("파일을 삭제 중 오류가 발생했습니다");
        }
    }
}
