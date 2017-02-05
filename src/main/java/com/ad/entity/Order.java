package com.ad.entity;

import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column
    private String name;
    @Column(name = "company_name")
    private String companyName;
    @Column
    private String model;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String remark;
    @Column(name = "created_at")
    private Date createdAt = new Date();
    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    public Order(){}

    public Order(PageOrderParam pageOrderParam) {
        this.name = pageOrderParam.getName();
        this.companyName = pageOrderParam.getCompanyName();
        this.model = pageOrderParam.getModel();
        this.address = pageOrderParam.getProvince() + pageOrderParam.getCity() + pageOrderParam.getArea();
        this.phone = pageOrderParam.getPhone();
        this.email = pageOrderParam.getEmail();
        this.remark = pageOrderParam.getRemark();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
