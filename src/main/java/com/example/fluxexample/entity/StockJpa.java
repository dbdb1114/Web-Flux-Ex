package com.example.fluxexample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class StockJpa extends AbstractStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer inventory;

    @Override
    public StockJpa decreaseInventory(Integer amount) {
        this.inventory -= amount;
        return this;
    }

    @Override
    public StockJpa increaseInventory(Integer amount) {
        this.inventory += amount;
        return this;
    }
}
