package com.ad.dao;

import com.ad.entity.Product;
import com.ad.entity.ProductFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductFileDao extends CrudRepository<ProductFile, Integer> {
    ProductFile findOneById(Integer id);
    Page<ProductFile> findAll(Pageable pageable);
    List<ProductFile> findAll();
    Page<ProductFile> findAll(Specification<ProductFile> spec, Pageable pageable);
    List<ProductFile> findByPdfURLIsNotNull();
    List<ProductFile> findByVideoLinkIsNotNull();
}
