package com.barreeyentos.catface.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.barreeyentos.catface.service.ImageNormalizer;

public class ImageNormalizerImplTest {

    ImageNormalizer normalizer;

    @Before
    public void setup() {
        normalizer = new ImageNormalizerImpl();
    }

    @Test
    public void shouldPadUnevenRows() {
        char[][] bad = new char[][] { { 'a', 'b', 'c' }, { 'a', 'b' }, { 'a' }, { 'a', 'a', 'a' } };

        char[][] normalized = normalizer.normalize(bad);

        char[][] expected = new char[][] { { 'a', 'b', 'c' }, { 'a', 'b', ' ' }, { 'a', ' ', ' ' }, { 'a', 'a', 'a' } };

        assertThat(normalized).isEqualTo(expected);
    }

}
