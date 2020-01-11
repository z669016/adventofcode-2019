package com.putoet.day15;

import java.util.Arrays;

public class ExtendableSurface implements IExtendableSurface {
    protected Paintable[][] surface = new Paintable[][] {{Paintable.unnkown()}};

    @Override
    public long count(Paintable tile) {
        return Arrays.stream(surface).flatMap(Arrays::stream).filter(elem -> elem == tile).count();
    }

    @Override
    public Paintable at(Point point) {
        int x = point.x();
        int y = point.y();
        resizeIfRequired(x, y);
        return surface[y][x];
    }

    @Override
    public void paint(Point point, Paintable newPaintable) {
        int x = point.x();
        int y = point.y();
        resizeIfRequired(x, y);
        surface[y][x] = newPaintable;
    }

    protected void resizeIfRequired(int x, int y) {
        if (y >= surface.length) extendDown(y);
        if (x >= surface[0].length) extendToTheRight(x);
    }

    protected void extendToTheRight(int newMaxX) {
        for (int idy = 0; idy < surface.length; idy++) {
            final Paintable[] newLine = new Paintable[newMaxX + 1];
            for (int idx = 0; idx < newMaxX + 1; idx++) {
                newLine[idx] = (idx < surface[idy].length ? surface[idy][idx] : Paintable.unnkown());
            }
            surface[idy] = newLine;
        }
    }

    protected void extendDown(int newMaxY) {
        final Paintable[][] newSurface = new Paintable[newMaxY + 1][];
        for (int idy = 0; idy < newMaxY + 1; idy++)
            newSurface[idy] = (idy < surface.length ? surface[idy] : emptyLine());
        surface = newSurface;
    }

    protected Paintable[] emptyLine() {
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
