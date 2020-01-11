package com.putoet.day15;

public class OriginExtendableSurface extends ExtendableSurface {
    private int originX = 0;
    private int originY = 0;

    @Override
    public Paintable at(Point point) {
        int x = point.x();
        int y = point.y();
        resizeIfRequired(x, y);
        return surface[y + originY][x + originX];
    }

    @Override
    public void paint(Point point, Paintable newPaintable) {
        int x = point.x();
        int y = point.y();
        resizeIfRequired(x, y);
        surface[y + originY][x + originX] = newPaintable;
    }

    @Override
    protected void resizeIfRequired(int x, int y) {
        if (y + originY  >= surface.length) extendDown(y + originY);
        if (y + originY < 0) {
            extendUp(surface.length + Math.abs(y + originY) - 1);
            originY += Math.abs(y + originY);
        }
        if (x + originX >= surface[0].length) extendToTheRight(x + originX);
        if (x + originX < 0) {
            extendToTheLeft(surface[0].length + Math.abs(x + originX) - 1);
            originX += Math.abs(x + originX);
        }
    }

    protected void extendToTheLeft(int newMaxX) {
        for (int idy = 0; idy < surface.length; idy++) {
            final Paintable[] newLine = new Paintable[newMaxX + 1];
            final int additionalLength = newMaxX + 1 - surface[idy].length;
            for (int idx = 0; idx < newMaxX + 1; idx++) {
                newLine[idx] = (idx < additionalLength ? Paintable.unnkown() : surface[idy][idx - additionalLength]);
            }
            surface[idy] = newLine;
        }
    }

    protected void extendUp(int newMaxY) {
        final Paintable[][] newSurface = new Paintable[newMaxY + 1][];
        final int additionalLength = newMaxY + 1 - surface.length;
        for (int idy = 0; idy < newMaxY + 1; idy++)
            newSurface[idy] = (idy < additionalLength ? emptyLine() : surface[idy - additionalLength]);
        surface = newSurface;
    }
}
