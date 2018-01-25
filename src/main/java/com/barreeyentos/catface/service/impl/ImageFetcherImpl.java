package com.barreeyentos.catface.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.barreeyentos.catface.exception.UnreachableImageException;
import com.barreeyentos.catface.service.ImageFetcher;

@Service
public class ImageFetcherImpl implements ImageFetcher {
    private static final Logger logger = LoggerFactory.getLogger(ImageFetcherImpl.class);

    @Override
    public char[][] fetch(String imageUrl) {
        try (Scanner scanner = getScanner(imageUrl)) {
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            char[][] image = new char[lines.size()][];
            for (int i = 0; i < lines.size(); ++i) {
                image[i] = lines.get(i).toCharArray();
            }
            logger.info("Fetched image of {} rows of data", lines.size());
            return image;
        }
    }

    private Scanner getScanner(String imageUrl) {
        try {
            logger.debug("Trying to fetch image: {}", imageUrl);
            URL url = new URL(imageUrl);
            return new Scanner(url.openStream());
        } catch (IOException e) {
            logger.error("Failed to fetch image: {}", imageUrl);
            throw new UnreachableImageException("Could not fetch image at url: " + imageUrl);
        }
    }

}
