package com.example.fluxexample.controller;

import com.example.fluxexample.entity.AbstractStock;
import com.example.fluxexample.entity.StockRedis;
import com.example.fluxexample.redis.StockRedisRepository;
import com.example.fluxexample.service.StockService;
import com.example.fluxexample.vo.RequestStock;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
@RestController
public class StockController {

    private final StockService stockService;
    private final StockRedisRepository repository;
    private final Sinks.Many<AbstractStock> sink;

    public StockController(StockService stockService, StockRedisRepository repository) {
        this.stockService = stockService;
        this.repository = repository;
        sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping("/")
    public Mono healthCheck1(){
        return Mono.just("HEALTHY DEFAULT PATH");
    }

    @GetMapping("/health-check")
    public Mono healthCheck(){
        return Mono.just("HEALTHY");
    }

    @GetMapping("/stock/find/all")
    public Mono findAll(){
        Iterable<StockRedis> all = repository.findAll();
        List<AbstractStock> stockList = new ArrayList<>();
        all.forEach(System.out::println);
        all.forEach(x->stockList.add(x));
        return Mono.just(stockList);
    }

    @GetMapping(value = "/stock/refresh/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<AbstractStock>> refreshAllStock(){
        return sink.asFlux()
                .map(stock->ServerSentEvent.builder(stock).build());
    }

    // load 할 때
    @GetMapping(value = "/stock/{productId}")
    public Mono<AbstractStock> getStock(@PathVariable Long productId){
        return Mono.just(stockService.lookUpStock(productId));
    }

    // load 이후
    @GetMapping(value = "/stock/refresh/{productId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<AbstractStock>> refreshStock(@PathVariable Long productId){
        return sink.asFlux()
                .filter(stock -> stock.getProductId() == productId)
                .map(stock->  ServerSentEvent.builder(stock).build());
    }

    @PutMapping("/stock/decrease")
    public Mono<? extends AbstractStock> decreaseStock(@RequestBody RequestStock requestStock){
        return Mono.just(stockService.decreaseStock(requestStock.getProductId(), requestStock.getAmount()))
                .log()
                .doOnNext(sink::tryEmitNext);
    }

    @PutMapping("/stock/increase")
    public Mono<? extends AbstractStock> increaseStock(@RequestBody RequestStock requestStock){
        return Mono.just(stockService.increaseStock(requestStock.getProductId(), requestStock.getAmount()))
                .log()
                .doOnNext(sink::tryEmitNext);
    }

}
