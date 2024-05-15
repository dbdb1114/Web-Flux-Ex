package com.example.fluxexample.service;

import com.example.fluxexample.entity.StockRedis;

public interface StockService {

    StockRedis lookUpStock(Long productId);
    StockRedis decreaseStock(Long productId, Integer amount);
    StockRedis increaseStock(Long productId, Integer amount);
}
