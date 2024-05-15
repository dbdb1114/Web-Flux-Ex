package com.example.fluxexample.controller;

import com.example.fluxexample.entity.StockRedis;
import com.example.fluxexample.redis.StockRedisRepository;
import com.example.fluxexample.service.StockService;

import com.example.fluxexample.vo.RequestStock;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootTest
class StockControllerTest {

    @Autowired
    StockService stockService;
    @Autowired
    StockRedisRepository repository;
    @Autowired
    StockController stockController;

    @Test
    void 벌크_삽입(){
        LongStream.range(0,11)
                .forEach(x->{
                    System.out.println("x = " + x);
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
    void 다건_동시_조회() {
        IntStream.range(1,10000)
                .parallel()
                .forEach(x->{
                    Optional<StockRedis> byId = repository.findById(1L);
                    System.out.println("*******************optional get 하기 전에 ");
                    System.out.println("*******************optionalById : " + byId);
                    System.out.println("*******************optional get 한다먄 ");
                    System.out.println("*******************optionalById.get() : " + byId.get());
                    System.out.println("******************************");
                    System.out.println("******************************");
                    System.out.println("******************************");
                    System.out.println("******************************");
                });
    }

    @Test
    void 다건_동시_재고변경() {
        IntStream.range(1,10000)
                .parallel()
                .forEach(x->{

                    System.out.println("***************Start**************");

                    System.out.println("*******************Stock data 조회");
                    StockRedis byId = repository.findByProductId(1L);

                    System.out.println("*******************Stock data get 및 재고변경");
                    StockRedis stock;

                    if(x % 2 == 1){
                        stock = byId.increaseInventory(1);
                    } else {
                        stock = byId.decreaseInventory(1);
                    }

                    System.out.println("*******************Stock data 저장 ");
                    repository.save(stock);

                    System.out.println("***************END**************");
                });
    }

    @Test
    void 저장_조회_동시처리(){
        IntStream.range(0,100000)
                .parallel()
                .forEach(x->{
                    StockRedis stockRedis = new StockRedis(11L,1);
                    if(x % 2 == 1){
                        System.out.println("조회");
                        조회함수.run();
                    } else {
                        System.out.println("삽입");
                        삽입함수.accept(stockRedis);
                    }
                });
    }

    @Test
    void 조회_값변경_저장() {
        IntStream.range(0,100)
                .parallel()
                .forEach(x -> repository.save(Stock조회.apply((long) x % 10).increaseInventory(1)));
    }

    @Test
    void 동일값_접근_및_변경() {
        Flux.range(0,100)
                .map(x->Stock조회.apply(2L))
                .log()
                .map(stock->stock.increaseInventory(100))
                .log()
                .map(stock->repository.save(stock))
                .log();
    }

    Function<Long, StockRedis> Stock조회 = id -> repository.findByProductId(id);
    Runnable 조회함수 = () -> repository.findById(1L);
    Consumer<StockRedis> 삽입함수 = stock -> repository.save(stock);

    @Test
    void 값변경 (){
        IntStream.range(1,10000)
                .parallel()
                .forEach(num->{
                    System.out.println("num = " + num);
                    StockRedis stockRedis = repository.findByProductId((long)num % 10);
                    stockRedis.increaseInventory(10000);
                });
    }

    @Test
    void 조회(){
        Iterable<StockRedis> all = repository.findAll();
        all.forEach(System.out::println);
    }

    @Test
    void 단건조회() {
        LongStream.range(1,10)
                        .forEach(x-> {
                            System.out.println(x);
                            Optional<StockRedis> byId = repository.findById(x);
                            System.out.println(byId.get());
                        });
    }


    @Test
    void 산발적인_감소 (){
        long l = System.currentTimeMillis();
        System.out.println("시작시간 : " + l);
        IntStream.range(1,10000)
                .parallel()
                .forEach(user->{
                    Long productId = (long)(Math.random() * 10) + 1;
                    System.out.println("user : " + user);

                    if((int) (Math.random() * 100) < 80){
                        stockService.decreaseStock(productId, (int)(Math.random() * 10));
                    } else {
                        stockService.increaseStock(productId, (int)(Math.random() * 10));
                    }

                });

        IntStream.range(1,10000)
                .parallel()
                .forEach(user->{
                    Long productId = (long)(Math.random() * 10) + 1;
                    System.out.println("user : " + user);
                    if((int) (Math.random() * 100) < 80){
                        stockService.decreaseStock(productId, (int)(Math.random() * 10));
                    } else {
                        stockService.increaseStock(productId, (int)(Math.random() * 10));
                    }

                });


        long e = System.currentTimeMillis();
        System.out.println("종료시간 : " + e);
        System.out.println("소요시간 : " + (e - l));

        Iterable<StockRedis> all = repository.findAll();
        all.forEach(System.out::println);
    }

    @Test
    void 산발적인_조회_및_감소(){
        IntStream.range(1,10000)
                .parallel()
                .forEach(user->{

                    Long productId = (long)(Math.random() * 10) + 1;
                    RequestStock requestStock = new RequestStock();
                    requestStock.setProductId(productId);
                    requestStock.setAmount(1);
                    stockController.getStock(productId);
                    if((int) (Math.random() * 100) < 80){
                        stockController.decreaseStock(requestStock);
                    } else {
                        stockController.increaseStock(requestStock);
                    }

                });
    }
}
