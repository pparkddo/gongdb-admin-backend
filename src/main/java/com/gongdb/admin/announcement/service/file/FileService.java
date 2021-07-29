package com.gongdb.admin.announcement.service.file;

import com.gongdb.admin.announcement.dto.response.FileDto;
import com.gongdb.admin.announcement.entity.UploadFile;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public UploadFile upload(MultipartFile file);

    public void delete(UploadFile uploadFile);

    public FileDto download(Long id);

}
