package com.shortener;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        System.out.println("Server started on port 8080");
    }
}