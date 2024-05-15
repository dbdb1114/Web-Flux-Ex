package com.example.fluxexample.config;

import com.example.fluxexample.entity.StockJpa;
import com.example.fluxexample.entity.StockRedis;
import org.springframework.stereotype.Component;

@Component
public class StockMapperImpl implements StockMapper{
    @Override
    public StockJpa toStockJpa(StockRedis stock) {
        return null;
    }

    @Override
    public StockRedis toStockRedis(StockJpa stock) {
        return StockRedis.builder()
                .productId(stock.getProductId())
                .inventory(stock.getInventory())
                .build();
    }
}
