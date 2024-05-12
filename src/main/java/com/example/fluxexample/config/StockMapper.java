package com.example.fluxexample.config;

import com.example.fluxexample.entity.Stock;
import com.example.fluxexample.entity.StockJpa;
import com.example.fluxexample.entity.StockRedis;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    StockJpa toStockJpa(StockRedis stock);
    StockRedis toStockRedis(StockJpa stock);
}
