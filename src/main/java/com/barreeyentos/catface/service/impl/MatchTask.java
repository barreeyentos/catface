package com.barreeyentos.catface.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barreeyentos.catface.dto.CatFace;
import com.barreeyentos.catface.dto.PartialImage;
import com.barreeyentos.catface.service.CatMatcher;

public class MatchTask extends RecursiveTask<List<CatFace>> {

    private static final Logger logger = LoggerFactory.getLogger(MatchTask.class);
    private static final long serialVersionUID = 1L;
    private static final int THRESHOLD = 6000;

    CatMatcher matcher;
    double confidence;
    char[][] image;
    List<PartialImage> partialImages;

    public MatchTask(CatMatcher matcher, char[][] image, List<PartialImage> partialImages, double confidence) {
        this.matcher = matcher;
        this.image = image;
        this.partialImages = partialImages;
        this.confidence = confidence;
    }

    @Override
    protected List<CatFace> compute() {

        if (partialImages.size() > THRESHOLD) {
            logger.trace("Splitting task in two lists of {}", partialImages.size() / 2);
            return ForkJoinTask.invokeAll(createSubtasks()).stream().map(ForkJoinTask::join).flatMap(Collection::stream)
                    .filter(Objects::nonNull).collect(Collectors.toList());
        } else {
            return process();
        }
    }

    private Collection<MatchTask> createSubtasks() {
        List<List<PartialImage>> images = ListUtils.partition(partialImages, partialImages.size() / 2);

        return images.stream().map(imageList -> new MatchTask(matcher, image, imageList, confidence))
                .collect(Collectors.toList());
    }

    private List<CatFace> process() {
        logger.trace("Processing batch of {}", partialImages.size());
        List<CatFace> allResults = new ArrayList<>();
        partialImages.forEach(partialImage -> {
            CatFace match = matcher.match(image, partialImage, confidence);
            if (Objects.nonNull(match)) {
                allResults.add(match);
            }
        });
        return allResults;
    }
}
