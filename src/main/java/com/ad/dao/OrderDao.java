package com.ad.dao;

import com.ad.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OrderDao extends CrudRepository<Order, Integer> {
    Page<Order> findAll(Pageable pageable);
}
