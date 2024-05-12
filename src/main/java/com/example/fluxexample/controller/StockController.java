package com.example.fluxexample.controller;

import com.example.fluxexample.entity.Stock;
import com.example.fluxexample.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stock/{productId}/{requestOrder}")
    public Mono<Stock> getStock(@PathVariable Long productId, @PathVariable Long requestOrder){
        log.info("this request Order is : {}", requestOrder );
        return stockService.lookUpStock(productId);
    }

    @PutMapping("/stock/decrease/{productId}/{amount}")
    public void decreaseStock(@PathVariable Long productId, @PathVariable Integer amount){
        stockService.decreaseStock(productId,amount);
    }

    @PutMapping("/stock/increase/{productId}/{amount}")
    public void increaseStock(@PathVariable Long productId, @PathVariable Integer amount){
        stockService.increaseStock(productId,amount);
    }

}
