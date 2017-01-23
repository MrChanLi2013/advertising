package com.ad.dao;


import com.ad.entity.AdminUser;
import org.springframework.data.repository.CrudRepository;

public interface AdminUserDao extends CrudRepository<AdminUser, Integer> {
    AdminUser findByUserName(String username);
}
