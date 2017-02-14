package com.ad.dao;

import com.ad.entity.News;
import com.ad.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsDao extends CrudRepository<News, Integer> {

    Page<News> findAll(Specification<News> spec, Pageable pageable);

    Page<News> findAll(Pageable pageable);

    List<News> findAll();
}
