package com.example.fluxexample.entity;

public abstract class AbstractStock implements Stock{
    abstract public Long getProductId();
    abstract public AbstractStock decreaseInventory(Integer amount);
    abstract public AbstractStock increaseInventory(Integer amount);
}
