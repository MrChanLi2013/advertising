package com.ad.service;

import com.ad.util.Utils;
import com.google.common.io.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    public void upload(MultipartFile uploadfile){
        String filename = uploadfile.getOriginalFilename();
        File file = new File(Utils.uploadPath() + filename);
        try {
            Files.write(uploadfile.getBytes(), file);
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败");
        }
    }
}
