package com.example.fluxexample.service;

import com.example.fluxexample.entity.AbstractStock;

public interface StockService {

    AbstractStock lookUpStock(Long productId);
    AbstractStock decreaseStock(Long productId, Integer amount);
    AbstractStock increaseStock(Long productId, Integer amount);
}
