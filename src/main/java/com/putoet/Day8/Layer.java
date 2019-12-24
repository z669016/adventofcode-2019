package com.putoet.Day8;

import java.util.Collections;
import java.util.List;

public class Layer {
    private final Dimension dimension;
    private final List<Integer> pixels;

    public static Layer of(Dimension dimension, List<Integer> pixels) {
        assert (dimension != null);
        assert (pixels.size() == dimension.size());

        return new Layer(dimension, pixels);
    }

    private Layer(Dimension dimension, List<Integer> pixels) {
        this.dimension = dimension;
        this.pixels = pixels;
    }

    public int count(Integer pixelValue) {
        return pixels.stream().reduce(0, (a, b) -> a + (b == pixelValue ? 1 : 0));
    }

    public List<Integer> pixels() {
        return Collections.unmodifiableList(pixels);
    }

    public void dump() {
        for (int idy = 0; idy < dimension.y(); idy++) {
            for (int idx = 0; idx < dimension.x(); idx++) {
                System.out.print(pixels.get(idy * dimension.y() + idx));
            }
            System.out.println();;
        }
    }
}
