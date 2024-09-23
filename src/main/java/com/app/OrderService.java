package com.app;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public String prepareBuyOrder(double triggerPrice, double currentPrice) {
        if (currentPrice <= triggerPrice) {
            return String.format("Buy Order Triggered at price: %.2f", currentPrice);
        }
        return "No Buy Order Triggered";
    }

    public String prepareSellOrder(double triggerPrice, double currentPrice) {
        if (currentPrice >= triggerPrice) {
            return String.format("Sell Order Triggered at price: %.2f", currentPrice);
        }
        return "No Sell Order Triggered";
    }
}