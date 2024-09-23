package com.app;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class WebSocketService {

    private final CoinDCXClient coinDCXClient;

    public WebSocketService() throws URISyntaxException {
//        URI uri = new URI("wss://api.coindcx.com/socket/v1");
//        this.coinDCXClient = new CoinDCXClient(uri);

        URI uri = new URI("wss://stream.coindcx.com/socket.io/?EIO=3&transport=websocket");
        this.coinDCXClient = new CoinDCXClient(uri);

    }

    public void connect() {
        coinDCXClient.connect();
    }

    public void disconnect() {
        coinDCXClient.close();
    }
}

