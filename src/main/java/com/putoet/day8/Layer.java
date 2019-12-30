package com.putoet.day8;

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

    public Dimension dimension() {
        return dimension;
    }

    public int count(Integer pixelValue) {
        return pixels.stream().reduce(0, (a, b) -> a + (b.equals(pixelValue) ? 1 : 0));
    }

    public List<Integer> pixels() {
        return Collections.unmodifiableList(pixels);
    }

    public Integer pixel(Integer x, Integer y) {
        assert (x >= 0 && x < dimension.x());
        assert (y >= 0 && y < dimension.y());

        return pixels.get(xyToOffset(x, y));
    }

    private int xyToOffset(int x, int y) {
        return y * dimension.x() + x;
    }

    public void dump() {
        for (int idy = 0; idy < dimension.y(); idy++) {
            for (int idx = 0; idx < dimension.x(); idx++) {
                System.out.print(pixels.get(xyToOffset(idx, idy)));
            }
            System.out.println();
        }
    }

    public void dumpAsImage() {
        for (int idy = 0; idy < dimension.y(); idy++) {
            for (int idx = 0; idx < dimension.x(); idx++) {
                System.out.print(pixels.get(xyToOffset(idx, idy)) == 0 ? ' ' : '#');
            }
            System.out.println();
        }
    }
}
