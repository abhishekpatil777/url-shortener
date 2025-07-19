package com.shortener.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.shortener.util.UrlStore;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import com.sun.net.httpserver.Headers;

public class ShortenHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // --- Set CORS Headers ---
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");

        // --- Handle preflight OPTIONS request ---
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONObject inputJson = new JSONObject(requestBody);
            String originalUrl = inputJson.getString("url");

            String shortCode = UUID.randomUUID().toString().substring(0, 6);
            UrlStore.save(shortCode, originalUrl);

            JSONObject responseJson = new JSONObject();
            responseJson.put("shortUrl", "http://localhost:8080/" + shortCode);

            byte[] responseBytes = responseJson.toString().getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}
