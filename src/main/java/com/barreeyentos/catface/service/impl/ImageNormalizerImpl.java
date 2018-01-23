package com.barreeyentos.catface.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.barreeyentos.catface.service.ImageNormalizer;

/*
 * ImageNormalizer ensures that an image is padded with spaces and has equal the same
 * number of columns in every row.
 */
@Service
public class ImageNormalizerImpl implements ImageNormalizer {
    private static final Logger logger = LoggerFactory.getLogger(ImageNormalizerImpl.class);
    private static final char EMPTY_SPACE = ' ';

    @Override
    public char[][] normalize(char[][] image) {
        int maxWidth = 0;
        for (int r = 0; r < image.length; r++) {
            if (maxWidth < image[r].length) {
                maxWidth = image[r].length;
            }
        }
        char[][] normalized = new char[image.length][maxWidth];
        for (int r = 0; r < image.length; ++r) {
            for (int c = 0; c < maxWidth; ++c) {
                if (c < image[r].length) {
                    normalized[r][c] = image[r][c];
                } else {
                    normalized[r][c] = EMPTY_SPACE;
                }
            }
        }
        logger.info("ImageNormalizer: normalizing to a {} x {} (w x h) image", maxWidth, image.length);
        return normalized;
    }

}
