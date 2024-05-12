package com.example.fluxexample.repository;

import com.example.fluxexample.entity.StockJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Long, StockJpa> {

    StockJpa findByProductId(Long productId);
}
