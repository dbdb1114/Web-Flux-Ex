package com.example.fluxexample.service;

import com.example.fluxexample.aop.DistributedLock;
import com.example.fluxexample.config.StockMapper;
import com.example.fluxexample.entity.AbstractStock;
import com.example.fluxexample.entity.StockRedis;
import com.example.fluxexample.redis.StockRedisRepository;
import com.example.fluxexample.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService{

    private final StockRedisRepository stockRedisRepository;
    private final StockRepository stockRepository;

    @Override
    public StockRedis lookUpStock(Long productId){
        return stockRedisRepository.findByProductId(productId);
    }

    @Override
    @DistributedLock(key="#productId")
    public StockRedis decreaseStock(Long productId, Integer amount) {
        StockRedis stockRedis = stockRedisRepository.findByProductId(productId);
        stockRedis = stockRedis.decreaseInventory(amount);
        return stockRedisRepository.save(stockRedis);
    }

    @Override
    @DistributedLock(key="#productId")
    public StockRedis increaseStock(Long productId, Integer amount) {
        StockRedis stockRedis = stockRedisRepository.findByProductId(productId);
        stockRedis = stockRedis.increaseInventory(amount);
        return stockRedisRepository.save(stockRedis);
    }

    public AbstractStock findInDbms(Long productId){
        return stockRepository.findByProductId(productId);
    }

}
