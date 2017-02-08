package com.ad.dao;

import com.ad.entity.Case;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CaseDao extends CrudRepository<Case, Integer> {
    List<Case> findAll();
}
