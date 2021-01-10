package com.putoet.day8;

import java.util.Collections;
import java.util.List;

public class Layer {
    private final Size size;
    private final List<Integer> pixels;

    public static Layer of(Size size, List<Integer> pixels) {
        assert (size != null);
        assert (pixels.size() == size.size());

        return new Layer(size, pixels);
    }

    private Layer(Size size, List<Integer> pixels) {
        this.size = size;
        this.pixels = pixels;
    }

    public Size dimension() {
        return size;
    }

    public int count(int pixelValue) {
        return pixels.stream().reduce(0, (a, b) -> a + (b.equals(pixelValue) ? 1 : 0));
    }

    public List<Integer> pixels() {
        return Collections.unmodifiableList(pixels);
    }

    public Integer pixel(int x, int y) {
        assert (x >= 0 && x < size.width());
        assert (y >= 0 && y < size.height());

        return pixels.get(xyToOffset(x, y));
    }

    private int xyToOffset(int x, int y) {
        return y * size.width() + x;
    }

    public void dump() {
        for (int idy = 0; idy < size.height(); idy++) {
            for (int idx = 0; idx < size.width(); idx++) {
                System.out.print(pixels.get(xyToOffset(idx, idy)));
            }
            System.out.println();
        }
    }

    public void dumpAsImage() {
        for (int idy = 0; idy < size.height(); idy++) {
            for (int idx = 0; idx < size.width(); idx++) {
                System.out.print(pixels.get(xyToOffset(idx, idy)) == 0 ? ' ' : '#');
            }
            System.out.println();
        }
    }
}
