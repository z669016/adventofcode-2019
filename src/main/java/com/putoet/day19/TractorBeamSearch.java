package com.putoet.day19;


import com.putoet.grid.Point;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

class TractorBeamSearch {
    private final Drone drone;
    private final Function<Integer, Integer> lowerBoundForRow;
    private final Function<Integer, Integer> upperBoundForRow;

    public TractorBeamSearch(@NotNull Drone drone,
                             @NotNull Function<Integer, Integer> lowerBoundForRow,
                             @NotNull Function<Integer, Integer> upperBoundForRow
    ) {
        this.drone = drone;
        this.lowerBoundForRow = lowerBoundForRow;
        this.upperBoundForRow = upperBoundForRow;
    }

    public Point squareTopLeft(int size) {
        final var startingPoint = findStartingPoint(size);

        var row = startingPoint.y();
        var step = 1;
        while (!containsSquare(row))
            row += step;

        return Point.of(upperForRow(row) - 99, row);
    }

    private boolean containsSquare(int y) {
        final var xValues = lowerAndUpperForRow(y);

        final var upperRight = Point.of(xValues.getValue1(), y);
        final var upperLeft = Point.of(upperRight.x() - 99, upperRight.y());
        final var bottomRight = Point.of(upperRight.x(), upperRight.y() + 99);
        final var bottomLeft = Point.of(upperLeft.x(), bottomRight.y());

        final var corners = List.of(upperLeft, upperRight, bottomRight, bottomLeft);
        return corners.stream().allMatch(point -> drone.state(point) == Drone.State.PULLED) ||
               corners.stream().allMatch(point -> drone.state(point.add(Point.WEST)) == Drone.State.PULLED);
    }

    private Point findStartingPoint(int size) {
        var y = 0;
        while (upperBoundForRow.apply(y) - lowerBoundForRow.apply(y) < size)
            y++;

        var pair = lowerAndUpperForRow(y);
        while (pair.getValue1() - pair.getValue0() < size) {
            pair = lowerAndUpperForRow(++y);
        }

        return Point.of(pair.getValue1(), y);
    }

    private Pair<Integer, Integer> lowerAndUpperForRow(int row) {
        return new Pair<>(lowerForRow(row), upperForRow(row));
    }

    private int lowerForRow(int row) {
        return lowerForRow(row, lowerBoundForRow.apply(row));
    }

    private int lowerForRow(int row, int start) {
        var x = start;
        while (drone.state(Point.of(x, row)) == Drone.State.STATIONARY)
            x++;

        return x;
    }

    private int upperForRow(int row) {
        return upperForRow(row, upperBoundForRow.apply(row));
    }

    private int upperForRow(int row, int start) {
        var x = start;
        while (drone.state(Point.of(x, row)) == Drone.State.STATIONARY)
            x--;

        return x;
    }
}
