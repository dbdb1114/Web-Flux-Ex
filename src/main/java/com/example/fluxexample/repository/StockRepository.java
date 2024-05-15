package com.example.fluxexample.repository;

import com.example.fluxexample.entity.StockJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockJpa,Long> {

    StockJpa findByProductId(Long productId);
}
