package com.ad.dao;

import com.ad.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDao extends CrudRepository<Product, Integer> {
    List<Product> findByParentId(Integer id);

    Product findOneById(Integer id);

    List<Product> findByLevel(Integer level);
}
