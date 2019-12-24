package com.putoet.Day8;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ImageTest {
    private static final Dimension dimension = Dimension.of(3, 2);
    private static final List<Integer> pixels = List.of(
            0, 1, 0, 1, 2, 1,
            1, 2, 1, 2, 0, 1,
            2, 2, 2, 1, 0, 2);
    private static final Image image = Image.of(dimension, pixels);

    @Test
    public void sizeTest() {
        assertEquals(pixels.size(), image.size());
    }

    @Test
    public void layerCountTest() {
        assertEquals(3, image.layersCount());
    }

    @Test
    public void layerTest() {
        assertEquals(List.of(0, 1, 0, 1, 2, 1), image.layer(0).pixels());
        assertEquals(List.of(1, 2, 1, 2, 0, 1), image.layer(1).pixels());
        assertEquals(List.of(2, 2, 2, 1, 0, 2), image.layer(2).pixels());
    }

    @Test
    public void findLayerWithLowerNumberOf() {
        Optional<Layer> layer = image.findLayerWithLowerNumberOf(0);
        assertFalse(layer.isEmpty());
        assertEquals(List.of(1, 2, 1, 2, 0, 1), layer.get().pixels());
    }
}