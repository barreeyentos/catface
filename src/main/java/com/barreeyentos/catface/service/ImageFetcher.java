package com.barreeyentos.catface.service;

@FunctionalInterface
public interface ImageFetcher {
    char[][] fetch(String imageUrl);
}
