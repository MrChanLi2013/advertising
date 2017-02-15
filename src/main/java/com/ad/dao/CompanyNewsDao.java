package com.ad.dao;

import com.ad.entity.CompanyNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyNewsDao extends CrudRepository<CompanyNews, Integer> {
    Page<CompanyNews> findAll(Pageable pageable);

    CompanyNews findFirstByOrderByCreatedAtDesc();

    List<CompanyNews> findAllByOrderByCreatedAtDesc();
}
