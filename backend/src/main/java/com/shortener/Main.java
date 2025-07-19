package com.shortener;

import com.shortener.handler.ShortenHandler;
import com.shortener.handler.RedirectHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/shorten", new ShortenHandler());

        // ✅ This is crucial for redirecting short URLs like /abc123
        server.createContext("/", new RedirectHandler());

        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        System.out.println("Server started on port 8080");
    }
}
