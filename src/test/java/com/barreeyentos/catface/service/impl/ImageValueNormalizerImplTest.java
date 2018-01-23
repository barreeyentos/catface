package com.barreeyentos.catface.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.barreeyentos.catface.service.ImageValueNormalizer;

public class ImageValueNormalizerImplTest {

    @Test
    public void test() {
        ImageValueNormalizer normalizer = new ImageValueNormalizerImpl();
        char[] image = { 'a', 'b', 'c' };

        int[] normalize = normalizer.normalize(image);
        assertThat(normalize).containsSequence(97, 98, 99);
    }

}
