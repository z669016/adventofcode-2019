package com.putoet.day19;


import com.putoet.grid.Point;
import org.javatuples.Pair;

import java.util.List;
import java.util.function.Function;

public class TractorBeamSearch {
    private final Drone drone;
    private final Function<Integer, Integer> lowerBoundForRow;
    private final Function<Integer, Integer> upperBoundForRow;

    public TractorBeamSearch(Drone drone, Function<Integer, Integer> lowerBoundForRow, Function<Integer, Integer> upperBoundForRow) {
        this.drone = drone;
        this.lowerBoundForRow = lowerBoundForRow;
        this.upperBoundForRow = upperBoundForRow;
    }

    public Point squareTopLeft(int size) {
        final Point startingPoint = findStartingPoint(size);

        int row = startingPoint.y();
        int step = 1;
        while (!containsSquare(row))
                row += step;

        return Point.of(upperForRow(row) - 99, row);
    }

    private boolean containsSquare(int y) {
        final Pair<Integer, Integer> xValues = lowerAndUpperForRow(y);

        final Point upperRight = Point.of(xValues.getValue1(), y);
        final Point upperLeft = Point.of(upperRight.x() - 99, upperRight.y());
        final Point bottomRight = Point.of(upperRight.x(), upperRight.y() + 99);
        final Point bottomLeft = Point.of(upperLeft.x(), bottomRight.y());

        final List<Point> corners = List.of(upperLeft, upperRight, bottomRight, bottomLeft);
        return corners.stream().allMatch(point -> drone.state(point) == Drone.State.PULLED) ||
                corners.stream().allMatch(point -> drone.state(point.add(Point.WEST)) == Drone.State.PULLED);
    }

    private void printRow(int minY) {
        for (int x = lowerBoundForRow.apply(minY); x < upperBoundForRow.apply(minY); x++)
            System.out.print(TractorBeamMap.stateToChar(drone.state(Point.of(x, minY))));
    }

    private Point findStartingPoint(int size) {
        int y = 0;
        while (upperBoundForRow.apply(y) - lowerBoundForRow.apply(y) < size)
            y++;

        Pair<Integer, Integer> pair = lowerAndUpperForRow(y);
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
        int x = start;
        while (drone.state(Point.of(x, row)) == Drone.State.STATIONARY)
            x++;

        return x;
    }

    private int upperForRow(int row) {
        return upperForRow(row, upperBoundForRow.apply(row));
    }

    private int upperForRow(int row, int start) {
        int x = start;
        while (drone.state(Point.of(x, row)) == Drone.State.STATIONARY)
            x--;

        return x;
    }
}
