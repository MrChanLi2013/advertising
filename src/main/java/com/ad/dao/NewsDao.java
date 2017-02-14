package com.ad.dao;

import com.ad.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface NewsDao extends CrudRepository<News, Integer> {
    Page<News> findAll(Pageable pageable);
}
