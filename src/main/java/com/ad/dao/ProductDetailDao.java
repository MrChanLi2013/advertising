package com.ad.dao;

import com.ad.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ProductDetailDao extends CrudRepository<ProductDetail, Integer> {
    ProductDetail findOneByProductId(Integer productId);
    Page<ProductDetail> findAll(Pageable pageable);
}
