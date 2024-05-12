package com.example.fluxexample.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@AllArgsConstructor
@RedisHash(value = "stock")
@ToString
public class StockRedis implements Stock{
    @Id
    private Long productId;
    private Integer inventory;

    public void decreaseInventory(Integer amount) {
        this.inventory -= amount;
    }

    public void increaseInventory(Integer amount) {
        this.inventory += amount;
    }
}
