package com.example.fluxexample.config;

import com.example.fluxexample.entity.StockJpa;
import com.example.fluxexample.entity.StockRedis;
import org.mapstruct.factory.Mappers;

public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    StockJpa toStockJpa(StockRedis stock);
    StockRedis toStockRedis(StockJpa stock);
}
