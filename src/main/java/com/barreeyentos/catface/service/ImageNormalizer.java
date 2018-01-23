package com.barreeyentos.catface.service;

@FunctionalInterface
public interface ImageNormalizer {
    char[][] normalize(char[][] image);
}
