package com.example.fluxexample.service;

import com.example.fluxexample.config.StockMapper;
import com.example.fluxexample.entity.Stock;
import com.example.fluxexample.redis.StockRedisRepository;
import com.example.fluxexample.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService{

    private final StockRedisRepository stockRedisRepository;
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Override
    public Mono<Stock> lookUpStock(Long productId) {
        return Mono.just(stockRedisRepository.findById(productId).orElse(
                stockRedisRepository.save(stockMapper.toStockRedis(stockRepository.findByProductId(productId)))
        ));
    }

    @Override
    public void decreaseStock(Long productId, Integer amount) {
        stockRedisRepository.findById(productId)
                .ifPresent(stock-> {
                    stock.decreaseInventory(amount);
                    stockRedisRepository.save(stock);
                });
    }

    @Override
    public void increaseStock(Long productId, Integer amount) {
        stockRedisRepository.findById(productId)
                .ifPresent(stock-> {
                    stock.increaseInventory(amount);
                    stockRedisRepository.save(stock);
                });
    }
}
