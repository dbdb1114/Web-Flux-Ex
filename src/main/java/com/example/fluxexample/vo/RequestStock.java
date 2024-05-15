package com.example.fluxexample.vo;

import com.example.fluxexample.entity.AbstractStock;
import com.example.fluxexample.entity.Stock;
import lombok.Data;

@Data
public class RequestStock implements Stock {
    Long productId;
    Integer amount;
}
