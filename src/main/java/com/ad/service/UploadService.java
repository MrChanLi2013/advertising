package com.ad.service;

import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {
    @Value("${products.path}")
    private String path;

    public void upload(MultipartFile uploadfile) {
        String filename = uploadfile.getOriginalFilename();
        File file = new File(path + filename);
        try {
            Files.write(uploadfile.getBytes(), file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }
}
