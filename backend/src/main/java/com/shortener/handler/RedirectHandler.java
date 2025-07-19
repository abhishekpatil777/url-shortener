package com.shortener.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.shortener.util.UrlStore;

import java.io.IOException;

public class RedirectHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String shortCode = path.substring(1); // Remove leading slash

        String originalUrl = UrlStore.get(shortCode);
        if (originalUrl != null) {
            exchange.getResponseHeaders().add("Location", originalUrl);
            exchange.sendResponseHeaders(302, -1); // Redirect
        } else {
            String notFoundMsg = "Short URL not found";
            exchange.sendResponseHeaders(404, notFoundMsg.length());
            exchange.getResponseBody().write(notFoundMsg.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
