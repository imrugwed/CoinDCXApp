package com.app;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class CoinDCXClient extends WebSocketClient {
    private Timer pingTimer;

    public CoinDCXClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected");

        // Start sending pings every 25 seconds
        startPing();
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Message received: " + message);
    }

    private void startPing() {
        pingTimer = new Timer();
        pingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isOpen()) {
                    send("2"); // Send a ping message
                } else {
                    stopPing(); // Stop pinging if not connected
                }
            }
        }, 0, 20000); // Send ping every 20 seconds
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason);
        stopPing();
        // Implement exponential backoff for reconnection
        new Thread(() -> {
            int delay = 5000; // Initial delay
            while (!isOpen()) {
                try {
                    System.out.println("Reconnecting to CoinDCX WebSocket...");
                    Thread.sleep(delay); // Wait before reconnecting
                    reconnect();
                    delay = Math.min(delay * 2, 30000); // Exponential backoff, max 30 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onError(Exception ex) {
        System.err.println("Error occurred: " + ex.getMessage());
        ex.printStackTrace();
    }

    private void stopPing() {
        if (pingTimer != null) {
            pingTimer.cancel();
            pingTimer = null;
        }
    }

    private void rreconnect() {
        try {
            System.out.println("Reconnecting to CoinDCX WebSocket...");
            this.connect();
        } catch (Exception e) {
            System.err.println("Error during reconnection: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        URI uri = new URI("wss://stream.coindcx.com/socket.io/?EIO= 3&transport=websocket");
        CoinDCXClient client = new CoinDCXClient(uri);
        client.connect();
    }
}
