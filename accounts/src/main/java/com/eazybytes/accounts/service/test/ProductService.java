package com.eazybytes.accounts.service.test;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public int calculateDiscount(int price) {
        if (price >= 1000) return price - 200;
        return price;
    }
}
