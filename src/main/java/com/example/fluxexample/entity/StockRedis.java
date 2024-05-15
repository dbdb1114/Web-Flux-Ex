package com.example.fluxexample.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@AllArgsConstructor
@RedisHash(value = "stock")
@ToString
@Getter
public class StockRedis extends AbstractStock {
    @Id
    private Long productId;
    private Integer inventory;

    public StockRedis decreaseInventory(Integer amount) {
        if(inventory - amount >= 0){
            this.inventory -= amount;
        }
        return this;
    }

    public StockRedis increaseInventory(Integer amount) {
        this.inventory += amount;
        return this;
    }
}
