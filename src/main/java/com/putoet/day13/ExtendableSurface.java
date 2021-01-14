package com.putoet.day13;

import java.util.Arrays;

public class ExtendableSurface {
    private Tile[][] surface = new Tile[][]{{TileFactory.of(0)}};

    public long count(Tile tile) {
        return Arrays.stream(surface).flatMap(Arrays::stream).filter(elem -> elem == tile).count();
    }

    public void set(int x, int y, Tile newTile) {
        if (y >= surface.length) extendVertically(y);
        if (x >= surface[0].length) extendHorizontally(x);

        surface[y][x] = newTile;
    }

    private void extendHorizontally(int x) {
        for (int idy = 0; idy < surface.length; idy++) {
            final Tile[] newLine = new Tile[x + 1];
            for (int idx = 0; idx < x + 1; idx++) {
                newLine[idx] = (idx < surface[idy].length ? surface[idy][idx] : TileFactory.EMPTY);
            }
            surface[idy] = newLine;
        }
    }

    private void extendVertically(int y) {
        final Tile[][] newSurface = new Tile[y + 1][];
        for (int idy = 0; idy < y + 1; idy++)
            newSurface[idy] = (idy < surface.length ? surface[idy] : emptyLine());
        surface = newSurface;
    }

    private Tile[] emptyLine() {
        final Tile[] newLine = new Tile[surface[0].length];
        Arrays.fill(newLine, TileFactory.EMPTY);
        return newLine;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Tile[] tiles : surface) {
            for (Tile tile : tiles) sb.append(tile.toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}
