package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AppRunner implements CommandLineRunner {

    private final WebSocketService webSocketService;
    private final OrderService orderService;

    @Autowired
    public AppRunner(WebSocketService webSocketService, OrderService orderService) {
        this.webSocketService = webSocketService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Buy Trigger Price: ");
        double buyTriggerPrice = scanner.nextDouble();

        System.out.println("Enter Sell Trigger Price: ");
        double sellTriggerPrice = scanner.nextDouble();

        webSocketService.connect();

        // Simulate received market price (in a real scenario, this would be dynamic)
        double currentPrice = 40000.0;  // Example market price

        System.out.println(orderService.prepareBuyOrder(buyTriggerPrice, currentPrice));
        System.out.println(orderService.prepareSellOrder(sellTriggerPrice, currentPrice));

        // For demonstration, we'll disconnect the WebSocket after handling
        webSocketService.disconnect();
    }
}
