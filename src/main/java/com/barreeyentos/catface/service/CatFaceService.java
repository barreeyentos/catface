package com.barreeyentos.catface.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.barreeyentos.catface.dto.CatFace;
import com.barreeyentos.catface.dto.CatFaceRequest;
import com.barreeyentos.catface.dto.PartialImage;

/*
 * CatFaceService takes a request and scans subsections of the image for
 * possible matches for cat faces.
 *
 * It returns all matches that are above the minimum requested threshold
 */
@Component
public class CatFaceService {
    private static final Logger logger = LoggerFactory.getLogger(CatFaceService.class);

    private CatMatcher catMatcher;
    private ImageDecomposer imageDecomposer;
    private ImageNormalizer imageNormalizer;
    private ImageFetcher imageFetcher;

    @Autowired
    public CatFaceService(CatMatcher catMatcher, ImageDecomposer imageDecomposer, ImageNormalizer imageNormalizer,
            ImageFetcher imageFetcher) {
        this.catMatcher = catMatcher;
        this.imageDecomposer = imageDecomposer;
        this.imageNormalizer = imageNormalizer;
        this.imageFetcher = imageFetcher;
    }

    public List<CatFace> findCatFaces(CatFaceRequest catfaceRequest) {
        List<CatFace> allResults = new ArrayList<>();

        char[][] rawImage = new char[0][0];
        if (Objects.nonNull(catfaceRequest.getImageUrl())) {
            rawImage = imageFetcher.fetch(catfaceRequest.getImageUrl());
        } else {
            rawImage = catfaceRequest.getImage();
        }

        char[][] normalizedImage = imageNormalizer.normalize(rawImage);
        logger.info("CatFaceService: analyzing {} x {} (w x h) image with min threshold of {}",
                normalizedImage[0].length, normalizedImage.length, catfaceRequest.getConfidenceThreshold());

        List<PartialImage> decomposedImages = imageDecomposer.decompose(normalizedImage);

        decomposedImages.forEach(image -> {
            CatFace match = catMatcher.match(image, catfaceRequest.getConfidenceThreshold());
            if (Objects.nonNull(match)) {
                allResults.add(match);
            }
        });

        // TODO: store results with original image to s3 to analyze and retrain model

        logger.info("CatFaceService: found {} cat faces in the image", allResults.size());
        return allResults;
    }
}
