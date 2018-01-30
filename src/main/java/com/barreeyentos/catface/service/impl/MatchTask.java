package com.barreeyentos.catface.service.impl;

import java.util.concurrent.RecursiveTask;

import com.barreeyentos.catface.dto.CatFace;
import com.barreeyentos.catface.dto.PartialImage;
import com.barreeyentos.catface.service.CatMatcher;

public class MatchTask extends RecursiveTask<CatFace> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    CatMatcher matcher;
    double confidence;
    char[][] image;
    PartialImage partialImage;

    public MatchTask(CatMatcher matcher, char[][] image, PartialImage partialImage, double confidence) {
        this.matcher = matcher;
        this.image = image;
        this.partialImage = partialImage;
        this.confidence = confidence;
    }

    @Override
    protected CatFace compute() {
        return matcher.match(image, partialImage, confidence);
    }

}
