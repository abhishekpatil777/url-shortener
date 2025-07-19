package com.shortener.util;

import java.util.concurrent.ConcurrentHashMap;

public class UrlStore {
    private static final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    public static void save(String shortCode, String originalUrl) {
        store.put(shortCode, originalUrl);
    }

    public static String get(String shortCode) {
        return store.get(shortCode);
    }
}
