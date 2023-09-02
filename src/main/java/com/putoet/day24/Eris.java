package com.putoet.day24;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.List;

record Eris(@NotNull Grid grid) {
    public static final char BUG = '#';
    public static final char EMPTY = '.';

    public static final List<Point> NEIGHBOURS = List.of(Point.NORTH, Point.EAST, Point.SOUTH, Point.WEST);

    public Eris evolve() {
        final var evolve = grid.copy();
        for (var y = 0; y < grid.maxY(); y++) {
            for (var x = 0; x < grid.maxX(); x++) {
                final var current = Point.of(x, y);
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
        var adjacent = NEIGHBOURS.stream().map(current::add).toList();
        return adjacent.stream()
                .filter(p -> grid.contains(p.x(), p.y()))
                .filter(p -> grid.get(p.x(), p.y()) == BUG)
                .count();
    }

    public long biodiversityRating() {
        var biodiversityRating = 0L;

        for (var y = 0; y < grid.maxY(); y++) {
            for (var x = 0; x < grid.maxX(); x++) {
                if (grid.get(x, y) == BUG)
                    biodiversityRating += (long) Math.pow(2, y * grid.maxY() + x);
            }
        }

        return biodiversityRating;
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
