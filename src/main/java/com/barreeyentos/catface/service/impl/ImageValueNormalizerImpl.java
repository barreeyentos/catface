package com.barreeyentos.catface.service.impl;

import org.springframework.stereotype.Service;

import com.barreeyentos.catface.service.ImageValueNormalizer;

/*
 * Simple data normalization by converting characters to integers
 * to avoid casts while making computations
 */
@Service
public class ImageValueNormalizerImpl implements ImageValueNormalizer {

    @Override
    public int[] normalize(char[] image) {
        int[] normalized = new int[image.length];

        for (int i = 0; i < image.length; ++i) {
            normalized[i] = image[i];
        }

        return normalized;
    }

}
