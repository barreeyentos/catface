package com.barreeyentos.catface.service;

@FunctionalInterface
public interface ImageValueNormalizer {
    int[] normalize(char[] image);
}
