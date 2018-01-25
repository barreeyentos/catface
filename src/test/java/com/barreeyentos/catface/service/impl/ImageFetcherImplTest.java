package com.barreeyentos.catface.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.barreeyentos.catface.controller.CatFacesImages;
import com.barreeyentos.catface.exception.UnreachableImageException;
import com.barreeyentos.catface.service.ImageFetcher;

public class ImageFetcherImplTest {

    ImageFetcher imageFetcher;

    @Before
    public void setup() {
        imageFetcher = new ImageFetcherImpl();
    }

    @Test
    public void testPerfectCatFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("CatFace_perfect_cat_image.txt").getFile());

        char[][] image = imageFetcher.fetch("file://" + file.getAbsolutePath());

        assertThat(image).isEqualTo(CatFacesImages.PERFECT_CAT);
    }

    @Test
    public void testEmptyImageFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("empty_image.txt").getFile());

        char[][] image = imageFetcher.fetch("file://" + file.getAbsolutePath());

        char[][] expected = new char[0][0];
        assertThat(image).isEqualTo(expected);
    }

    @Test(expected = UnreachableImageException.class)
    public void testNonExistantFile() {

        imageFetcher.fetch("file://idontexist");

    }

}
