package com.example.fluxexample.service;

import com.example.fluxexample.config.StockMapper;
import com.example.fluxexample.entity.AbstractStock;
import com.example.fluxexample.entity.Stock;
import com.example.fluxexample.entity.StockRedis;
import com.example.fluxexample.redis.StockRedisRepository;
import com.example.fluxexample.repository.StockRepository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService{

    private final StockRedisRepository stockRedisRepository;
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Override
    public AbstractStock lookUpStock(Long productId) {
        Optional<StockRedis> stockRedis = stockRedisRepository.findById(productId);
        stockRedis = stockNpeHandler(stockRedis, productId);
        return stockRedis.get();
    }

    @Override
    public AbstractStock decreaseStock(Long productId, Integer amount) {
        return lookUpStock(productId).decreaseInventory(amount);
    }

    @Override
    public AbstractStock increaseStock(Long productId, Integer amount) {
        return lookUpStock(productId).increaseInventory(amount);
    }

    public AbstractStock findInDbms(Long productId){
        return stockRepository.findByProductId(productId);
    }

    public Optional<StockRedis> stockNpeHandler(Optional<StockRedis> stockRedis, Long productId){
        for (int i = 0; i < 5; i++) {
            if(stockRedis.isEmpty()){
                stockRedis = stockRedisRepository.findById(productId);
            }
        }
        return stockRedis;
    }
}
