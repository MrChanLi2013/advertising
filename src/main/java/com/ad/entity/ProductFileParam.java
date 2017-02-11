package com.ad.entity;

import org.springframework.web.multipart.MultipartFile;

public class ProductFileParam {
    private String name;
    private MultipartFile pdfFile;
    private String videoLink;
    private String videoDesc;

    private MultipartFile[] imaFiles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(MultipartFile pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public MultipartFile[] getImaFiles() {
        return imaFiles;
    }

    public void setImaFiles(MultipartFile[] imaFiles) {
        this.imaFiles = imaFiles;
    }
}
