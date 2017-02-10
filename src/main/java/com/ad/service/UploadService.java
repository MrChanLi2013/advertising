package com.ad.service;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    @Value("${products.path}")
    private String path;
    @Value("${products.file.path}")
    private String filePath;

    public void upload(MultipartFile uploadfile) {
        String filename = uploadfile.getOriginalFilename();
        File file = new File(path + filename);
        try {
            Files.write(uploadfile.getBytes(), file);
        } catch (IOException e) {
            logger.error("上传文件失败:", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    public void uploadFile(MultipartFile uploadfile) {
        String filename = uploadfile.getOriginalFilename();
        File file = new File(filePath + filename);
        try {
            Files.write(uploadfile.getBytes(), file);
        } catch (IOException e) {
            logger.error("上传文件失败:", e);
            throw new RuntimeException("上传文件失败");
        }
    }
}
