package com.barreeyentos.catface.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.barreeyentos.catface.dto.PartialImage;
import com.barreeyentos.catface.dto.Position;
import com.barreeyentos.catface.service.ImageDecomposer;

public class ImageDecomposerImplTest {

    private static final double HEIGHT_FACTOR = .5;
    private static final double WIDTH_FACTOR = .5;
    private static final int HEIGHT = 4;
    private static final int WIDTH = 6;
    private ImageDecomposer decomposer;

    @Before
    public void setup() {
        decomposer = new ImageDecomposerImpl(WIDTH, HEIGHT, WIDTH_FACTOR, HEIGHT_FACTOR);
    }

    @Test
    public void shouldHandleEvenlySplitImage() {

        char[][] testImage = createTestImage(WIDTH * 3, HEIGHT * 3);
        List<PartialImage> decompose = decomposer.decompose(testImage);

        assertThat(decompose).hasSize(36);

    }

    @Test
    public void shouldHandleOddlySplitImages() {

        char[][] testImage = createTestImage(WIDTH * 2 + 1, HEIGHT * 3 + 1);
        List<PartialImage> decompose = decomposer.decompose(testImage);

        assertThat(decompose).hasSize(35);

    }

    @Test
    public void shouldHaveCorrectPositionsWhenPerfectlyDecomposed() {
        char[][] testImage = createTestImage(WIDTH, HEIGHT);
        List<PartialImage> decompose = decomposer.decompose(testImage);

        assertThat(decompose).hasSize(4);
        List<Position> positions = decompose.stream().map(PartialImage::getPosition).collect(Collectors.toList());
        assertThat(positions).containsExactlyInAnyOrder(Position.of(0, 0), Position.of(WIDTH / 2, 0),
                Position.of(0, HEIGHT / 2), Position.of(WIDTH / 2, HEIGHT / 2));
    }

    @Test
    public void shouldHaveCorrectPositionsWhenDecomposedImageGoesBeyondOriginal() {
        char[][] testImage = createTestImage(WIDTH + 1, HEIGHT + 1);
        List<PartialImage> decompose = decomposer.decompose(testImage);

        assertThat(decompose).hasSize(9);
        List<Position> positions = decompose.stream().map(PartialImage::getPosition).collect(Collectors.toList());
        assertThat(positions).containsExactlyInAnyOrder(Position.of(0, 0), Position.of(WIDTH / 2, 0),
                Position.of(WIDTH, 0), Position.of(0, HEIGHT / 2), Position.of(WIDTH / 2, HEIGHT / 2),
                Position.of(WIDTH, HEIGHT / 2), Position.of(0, HEIGHT), Position.of(WIDTH / 2, HEIGHT),
                Position.of(WIDTH, HEIGHT));
    }

    @Test
    public void shouldHaveCorrectCharactersWhenEvenlySplit() {
        char[][] testImage = { { 'a', 'b', 'c', 'd', 'e', 'f' },
                //
                { 'g', 'h', 'i', 'j', 'k', 'l' },
                //
                { 'm', 'n', 'o', 'p', 'q', 'r' },
                //
                { 's', 't', 'u', 'v', 'w', 'x' } };
        List<PartialImage> decompose = decomposer.decompose(testImage);

        assertThat(decompose).hasSize(4);

        PartialImage expectedImage1 = new PartialImage();
        expectedImage1.setPosition(Position.of(0, 0));
        expectedImage1.setImage(new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x' });
        PartialImage expectedImage2 = new PartialImage();
        expectedImage2.setPosition(Position.of(3, 0));
        expectedImage2.setImage(new char[] { 'd', 'e', 'f', ' ', ' ', ' ', 'j', 'k', 'l', ' ', ' ', ' ', 'p', 'q', 'r',
                ' ', ' ', ' ', 'v', 'w', 'x', ' ', ' ', ' ', });
        PartialImage expectedImage3 = new PartialImage();
        expectedImage3.setPosition(Position.of(0, 2));
        expectedImage3.setImage(new char[] { 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', ' ', ' ', ' ',
                ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' });
        PartialImage expectedImage4 = new PartialImage();
        expectedImage4.setPosition(Position.of(3, 2));
        expectedImage4.setImage(new char[] { 'p', 'q', 'r', ' ', ' ', ' ', 'v', 'w', 'x', ' ', ' ', ' ', ' ', ' ', ' ',
                ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', });

        assertThat(decompose).containsExactlyInAnyOrder(expectedImage1, expectedImage2, expectedImage3, expectedImage4);

    }

    private char[][] createTestImage(int width, int height) {

        char[][] image = new char[height][width];

        for (int r = 0; r < height; ++r) {
            for (int c = 0; c < width; ++c) {
                image[r][c] = (char) ('0' + c);
            }
        }

        return image;

    }

}
