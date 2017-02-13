package com.ad.web.controller.admin;

import com.ad.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
public class UploadController {
    @Value("${newsFile.path}")
    private String newsFilePath;
    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/upload-news-picture", method = RequestMethod.POST)
    public String uploadNewsPicture(MultipartFile file) {
        uploadService.uploadNewsFile(file);
        return newsFilePath + file.getOriginalFilename();
    }
}
