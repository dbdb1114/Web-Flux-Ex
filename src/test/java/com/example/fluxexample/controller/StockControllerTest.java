package com.example.fluxexample.controller;

import com.example.fluxexample.entity.StockRedis;
import com.example.fluxexample.redis.StockRedisRepository;
import com.example.fluxexample.service.StockService;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Slf4j
@SpringBootTest
class StockControllerTest {

    @Autowired
    StockService stockService;
    @Autowired
    StockRedisRepository repository;

    @Test
    void 벌크_삽입(){
        LongStream.range(1,10)
                .forEach(x->{
                    int inventory = (int) (Math.random() * 1000);
                    StockRedis stockRedis = StockRedis.builder()
                            .productId(x)
                            .inventory(inventory)
                            .build();
                    log.info("sotckRedis : {}", stockRedis);
                    repository.save(stockRedis);
                });
    }

    @Test
    void 조회(){
        Iterable<StockRedis> all = repository.findAll();
        all.forEach(System.out::println);
    }


    @Test
    void 산발적인_감소 (){
        IntStream.range(1,10000)
                .forEach(user->{
                    Long productId = (long)(Math.random() * 10);

                    if((int) (Math.random() * 100) < 80){
                        stockService.decreaseStock(productId, (int)(Math.random() * 10));
                    } else {
                        stockService.increaseStock(productId, (int)(Math.random() * 10));
                    }

                });

        IntStream.range(1,10000)
                .forEach(user->{
                    Long productId = (long)(Math.random() * 10);

                    if((int) (Math.random() * 100) < 80){
                        stockService.decreaseStock(productId, (int)(Math.random() * 10));
                    } else {
                        stockService.increaseStock(productId, (int)(Math.random() * 10));
                    }

                });
    }
}