package com.putoet.day15;

import java.util.Arrays;

public class ExtendableSurface {
    private Paintable[][] surface = new Paintable[][] {{Paintable.unnkown()}};

    public long count(Paintable tile) {
        return Arrays.stream(surface).flatMap(Arrays::stream).filter(elem -> elem == tile).count();
    }

    public void paint(int x, int y, Paintable newPaintable) {
        if (y >= surface.length) extendVertically(y);
        if (x >= surface[0].length) extendHorizontally(x);

        surface[y][x] = newPaintable;
    }

    private void extendHorizontally(int x) {
        for (int idy = 0; idy < surface.length; idy++) {
            final Paintable[] newLine = new Paintable[x + 1];
            for (int idx = 0; idx < x + 1; idx++) {
                newLine[idx] = (idx < surface[idy].length ? surface[idy][idx] : Paintable.unnkown());
            }
            surface[idy] = newLine;
        }
    }

    private void extendVertically(int y) {
        final Paintable[][] newSurface = new Paintable[y + 1][];
        for (int idy = 0; idy < y + 1; idy++)
            newSurface[idy] = (idy < surface.length ? surface[idy] : emptyLine());
        surface = newSurface;
    }

    private Paintable[] emptyLine() {
        final Paintable[] newLine = new Paintable[surface[0].length];
        Arrays.fill(newLine, Paintable.unnkown());
        return newLine;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Paintable[] paintables : surface) {
            for (Paintable paintable : paintables) sb.append(paintable.paint());
            sb.append('\n');
        }
        return sb.toString();
    }
}
