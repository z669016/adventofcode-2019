package com.putoet.day8;

import com.putoet.grid.Size;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

record Layer(Size size, List<Integer> pixels) {
    public static Layer of(@NotNull Size size, @NotNull List<Integer> pixels) {
        assert (pixels.size() == size.count());

        return new Layer(size, pixels);
    }

    public Size size() {
        return size;
    }

    public int count(int pixelValue) {
        return pixels.stream().reduce(0, (a, b) -> a + (b.equals(pixelValue) ? 1 : 0));
    }

    public List<Integer> pixels() {
        return Collections.unmodifiableList(pixels);
    }

    public Integer pixel(int x, int y) {
        assert (x >= 0 && x < size.dx());
        assert (y >= 0 && y < size.dy());

        return pixels.get(xyToOffset(x, y));
    }

    private int xyToOffset(int x, int y) {
        return y * size.dx() + x;
    }

    public void dumpAsImage() {
        for (int idy = 0; idy < size.dy(); idy++) {
            for (int idx = 0; idx < size.dx(); idx++) {
                System.out.print(pixels.get(xyToOffset(idx, idy)) == 0 ? ' ' : '#');
            }
            System.out.println();
        }
    }
}
