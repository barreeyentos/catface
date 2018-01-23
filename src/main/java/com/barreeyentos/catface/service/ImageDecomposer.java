package com.barreeyentos.catface.service;

import java.util.List;

import com.barreeyentos.catface.dto.PartialImage;

@FunctionalInterface
public interface ImageDecomposer {
    List<PartialImage> decompose(char[][] image);
}
