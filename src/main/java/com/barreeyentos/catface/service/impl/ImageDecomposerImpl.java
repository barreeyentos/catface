package com.barreeyentos.catface.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.barreeyentos.catface.dto.PartialImage;
import com.barreeyentos.catface.dto.Position;
import com.barreeyentos.catface.service.ImageDecomposer;

/*
 * ImageDecomposer takes an image and extracts as many smaller images as possible that
 * are of the same dimensions as the dimensions of the perfect cat image
 * The widthFactor and heightFactor control how large of steps to take horizontally and
 * vertically from the starting position.
 */
@Service
public class ImageDecomposerImpl implements ImageDecomposer {
    private static final Logger logger = LoggerFactory.getLogger(ImageDecomposerImpl.class);

    private static final char EMPTY_SPACE = ' ';
    private int width;
    private int height;
    private double widthFactor;
    private double heightFactor;

    @Autowired
    public ImageDecomposerImpl(@Value("${decompose.width}") int width, @Value("${decompose.height}") int height,
            @Value("${decompose.widthFactor}") double widthFactor,
            @Value("${decompose.heightFactor}") double heightFactor) {
        this.width = width;
        this.height = height;
        this.widthFactor = widthFactor;
        this.heightFactor = heightFactor;
    }

    @Override
    public List<PartialImage> decompose(char[][] image) {
        List<PartialImage> partialImages = new ArrayList<>();

        logger.debug("ImageDecomposerImpl: decomposing image of size {} x {} (w x h)", image[0].length, image.length);

        int widthIncrement = (int) Math.floor(width * widthFactor);
        int heightIncrement = (int) Math.floor(height * heightFactor);
        int widthImages = (int) Math.ceil(image[0].length / (widthIncrement * 1.0));
        int heightImages = (int) Math.ceil(image.length / (heightIncrement * 1.0));

        logger.debug("ImageDecomposerImpl: Breaking into {} images", widthImages * heightImages);

        for (int w = 0; w < widthImages; w++) {
            for (int h = 0; h < heightImages; h++) {
                Position originalPosition = Position.of(w * widthIncrement, h * heightIncrement);
                char[] newImage = createFromImage(image, originalPosition);

                PartialImage partialImage = new PartialImage();
                partialImage.setPosition(originalPosition);
                partialImage.setImage(newImage);

                partialImages.add(partialImage);
            }
        }

        return partialImages;
    }

    private char[] createFromImage(char[][] image, Position originalPosition) {
        char[] decomposed = new char[width * height];
        for (int r = 0; r < height; ++r) {
            for (int c = 0; c < width; ++c) {
                if (c + originalPosition.getX() < image[0].length && r + originalPosition.getY() < image.length) {
                    decomposed[c + r * width] = image[r + originalPosition.getY()][c + originalPosition.getX()];
                } else {
                    decomposed[c + r * width] = EMPTY_SPACE;
                }
            }
        }
        return decomposed;
    }

}
