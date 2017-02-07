package com.ad.dao;

import com.ad.entity.ProductFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ProductFileDao extends CrudRepository<ProductFile, Integer> {
    ProductFile findOneById(Integer id);
    Page<ProductFile> findAll(Pageable pageable);
}
