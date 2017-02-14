package com.ad.dao;

import com.ad.entity.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsDao extends CrudRepository<News, Integer> {
}
