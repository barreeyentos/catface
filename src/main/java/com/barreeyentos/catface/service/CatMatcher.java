package com.barreeyentos.catface.service;

import com.barreeyentos.catface.dto.CatFace;
import com.barreeyentos.catface.dto.PartialImage;

@FunctionalInterface
public interface CatMatcher {
    CatFace match(PartialImage image, double confidenceThreshold);
}
