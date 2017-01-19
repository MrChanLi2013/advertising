package com.ad.entity;

import org.springframework.web.multipart.MultipartFile;

public class PageProductParam {
    private String name;
    private Integer parentId;
    private String model;
    private String remark;
    private MultipartFile img;
    private MultipartFile detail;
    private String meaterial;
    private String size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMeaterial() {
        return meaterial;
    }

    public void setMeaterial(String meaterial) {
        this.meaterial = meaterial;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    public MultipartFile getDetail() {
        return detail;
    }

    public void setDetail(MultipartFile detail) {
        this.detail = detail;
    }
}
