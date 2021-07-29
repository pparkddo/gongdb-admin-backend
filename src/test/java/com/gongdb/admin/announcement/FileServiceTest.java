package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.gongdb.admin.announcement.dto.response.FileDto;
import com.gongdb.admin.announcement.entity.UploadFile;
import com.gongdb.admin.announcement.repository.UploadFileRepository;
import com.gongdb.admin.announcement.service.file.FileService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class FileServiceTest {
    
    @Autowired private FileService fileService;
    @Autowired private UploadFileRepository uploadFileRepository;

    @Test
    public void uploadTest() {
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());
        fileService.upload(file);

        assertEquals(1, uploadFileRepository.count());
    }

    @Test
    public void downloadTest() {
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());
        UploadFile uploadFile = fileService.upload(file);

        FileDto dto = fileService.download(uploadFile.getId());

        assertNotNull(dto.getResource());
    }
}
