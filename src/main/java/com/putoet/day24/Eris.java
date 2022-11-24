package com.putoet.day24;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point;

import java.util.List;
import java.util.stream.Collectors;

public class Eris {
    public static final char BUG = '#';
    public static final char EMPTY = '.';

    public static final List<Point> NEIGHBOURS = List.of(Point.NORTH, Point.EAST, Point.SOUTH, Point.WEST);
    private final Grid grid;

    public Eris(Grid grid) {
        assert grid != null;

        this.grid = grid;
    }

    public Eris evolve() {
        final Grid evolve = grid.copy();
        for (int y = 0; y < grid.maxY(); y++) {
            for (int x = 0; x < grid.maxX(); x++) {
                final Point current = Point.of(x, y);
                final long adjacentBugCount = adjacentBugCount(current);

                if (grid.get(x, y) == BUG)
                    evolve.set(x, y, adjacentBugCount == 1L ? BUG : EMPTY);
                else
                    evolve.set(x, y, adjacentBugCount == 1L || adjacentBugCount == 2L ? BUG : EMPTY);
            }
        }

        return new Eris(evolve);
    }

    private long adjacentBugCount(Point current) {
        final List<Point> adjacent = NEIGHBOURS.stream().map(current::add).collect(Collectors.toList());
        return adjacent.stream()
                .filter(p -> grid.contains(p.x(), p.y()))
                .filter(p -> grid.get(p.x(), p.y()) == BUG)
                .count();
    }

    public long biodiversityRating() {
        long biodiversityRating = 0;

        for (int y = 0; y < grid.maxY(); y++) {
            for (int x = 0; x < grid.maxX(); x++) {
                if (grid.get(x, y) == BUG)
                    biodiversityRating += Math.pow(2, y * grid.maxY() + x);
            }
        }

        return biodiversityRating;
    }

    protected boolean equals(char[][] other) {
        assert other != null;

        return GridUtils.gridEquals(grid.grid(), other);
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
