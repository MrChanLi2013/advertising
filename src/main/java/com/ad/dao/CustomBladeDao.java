package com.ad.dao;

import com.ad.entity.CustomBlade;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomBladeDao extends CrudRepository<CustomBlade, Integer> {
    List<CustomBlade> findAll();
}
