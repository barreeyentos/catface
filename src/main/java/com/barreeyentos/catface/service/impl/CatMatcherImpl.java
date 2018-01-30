package com.barreeyentos.catface.service.impl;

import static com.barreeyentos.catface.service.impl.CatFaceThetaConstants.PERFECT_THETA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.barreeyentos.catface.dto.CatFace;
import com.barreeyentos.catface.dto.Dimension;
import com.barreeyentos.catface.dto.PartialImage;
import com.barreeyentos.catface.service.CatMatcher;

/*
 * CatMatcher creates a prediction on whether or not an image is cat face
 *
 */
@Service
public class CatMatcherImpl implements CatMatcher {
    private static final Logger logger = LoggerFactory.getLogger(CatMatcherImpl.class);
    private static char EMPTY_SPACE = ' ';

    @Override
    public CatFace match(char[][] image, PartialImage partialImage, double confidenceThreshold) {
        CatFace matchedCatface = null;

        double confidence = predict(image, partialImage);

        if (confidence >= confidenceThreshold) {
            matchedCatface = new CatFace();
            matchedCatface.setConfidence(confidence);
            matchedCatface.setPosition(partialImage.getPosition());
            matchedCatface.setDimension(partialImage.getDimension());

            logger.debug("CatMatcherImpl: found a match with confidence {} at position {}", confidence,
                    partialImage.getPosition());
        } else {
            logger.trace("CatMatcherImpl: not a match with confidence {} at position {}", confidence,
                    partialImage.getPosition());
        }

        return matchedCatface;
    }

    private double predict(char[][] image, PartialImage partialImage) {
        Dimension dimension = partialImage.getDimension();
        int startRow = partialImage.getPosition().getY();
        int startColumn = partialImage.getPosition().getX();

        if (dimension.getWidth() * dimension.getHeight() == PERFECT_THETA.length) {
            double prediction = 0.0;
            int i = 0;
            for (int row = startRow; row < startRow + dimension.getHeight(); ++row) {
                for (int column = startColumn; column < startColumn + dimension.getWidth(); ++column) {
                    prediction += PERFECT_THETA[i++] * normalizePixel(image, row, column);
                }
            }

            prediction = sigmoid(prediction);
            logger.trace("CatMatcherImpl: predicting {}% cat face", prediction);
            return prediction;
        } else {
            logger.error("CatMatcherImpl: normalizedImage length doesn't match coefficient length.  Can't predict");
            return 0;
        }

    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private int normalizePixel(char[][] image, int row, int column) {
        if (row < image.length && column < image[row].length) {
            return image[row][column];
        } else {
            return (EMPTY_SPACE);
        }
    }
}
