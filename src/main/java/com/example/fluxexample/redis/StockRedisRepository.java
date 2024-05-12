package com.example.fluxexample.redis;

import com.example.fluxexample.entity.StockRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRedisRepository extends CrudRepository<StockRedis,Long> {
}
