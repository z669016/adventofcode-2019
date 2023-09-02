package com.putoet.day8;

import com.putoet.grid.Size;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ImageTest {
    private static final Size SIZE = new Size(3, 2);
    private static final List<Integer> pixels = List.of(
            0, 1, 0, 1, 2, 1,
            1, 2, 1, 2, 0, 1,
            2, 2, 2, 1, 0, 2);
    private static final Image image = Image.of(SIZE, pixels);

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
        final var layer = image.findLayerWithLowerNumberOf(0);
        assertFalse(layer.isEmpty());
        assertEquals(List.of(1, 2, 1, 2, 0, 1), layer.get().pixels());
    }

    @Test
    public void testDecode() {
        final var size = new Size(2, 2);
        final var image = Image.of(size, List.of(0, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 2, 0, 0, 0, 0));
        final var decodedLayer = image.decode();
        assertEquals(List.of(0, 1, 1, 0), decodedLayer.pixels());
    }
}