package com.example.fluxexample.service;

import com.example.fluxexample.entity.Stock;
import reactor.core.publisher.Mono;

public interface StockService {

    Mono<Stock> lookUpStock(Long productId);
    void decreaseStock(Long productId, Integer amount);
    void increaseStock(Long productId, Integer amount);
}
