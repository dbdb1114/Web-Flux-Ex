package com.example.fluxexample.entity;

import lombok.Getter;

@Getter
public class StockJpa implements Stock{
    private Long id;
    private Long productId;
    private Integer inventory;
}
