package com.barreeyentos.catface.service.impl;

import static com.barreeyentos.catface.service.impl.CatFaceThetaConstants.PERFECT_THETA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barreeyentos.catface.dto.CatFace;
import com.barreeyentos.catface.dto.PartialImage;
import com.barreeyentos.catface.service.CatMatcher;
import com.barreeyentos.catface.service.ImageValueNormalizer;

/*
 * CatMatcher creates a prediction on whether or not an image is cat face
 *
 */
@Service
public class CatMatcherImpl implements CatMatcher {
    private static final Logger logger = LoggerFactory.getLogger(CatMatcherImpl.class);

    private ImageValueNormalizer imageNormalizer;

    @Autowired
    public CatMatcherImpl(ImageValueNormalizer imageNormalizer) {
        this.imageNormalizer = imageNormalizer;
    }

    @Override
    public CatFace match(PartialImage image, double confidenceThreshold) {
        CatFace matchedCatface = null;

        int[] normalizedImage = imageNormalizer.normalize(image.getImage());

        double confidence = predict(normalizedImage);

        if (confidence >= confidenceThreshold) {
            matchedCatface = new CatFace();
            matchedCatface.setConfidence(confidence);
            matchedCatface.setPosition(image.getPosition());

            logger.debug("CatMatcherImpl: found a match with confidence {} at position {}", confidence,
                    image.getPosition());
        } else {
            logger.trace("CatMatcherImpl: not a match with confidence {} at position {}", confidence,
                    image.getPosition());
        }

        return matchedCatface;
    }

    private double predict(int[] normalizedImage) {
        if (normalizedImage.length == PERFECT_THETA.length) {
            double prediction = 0.0;
            for (int i = 0; i < PERFECT_THETA.length; ++i) {
                prediction += PERFECT_THETA[i] * normalizedImage[i];
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

}
