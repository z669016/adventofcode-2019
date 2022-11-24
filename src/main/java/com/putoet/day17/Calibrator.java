package com.putoet.day17;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Calibrator {
    public static final String RIGHT_COMMAND = "R";
    public static final String LEFT_COMMAND = "L";
    public static char SCAFFOLD = '#';
    public static char ROBOT_UP = '^';

    private final Grid grid;

    public Calibrator(Grid grid) {
        this.grid = grid;
    }

    public List<Point> intersections() {
        final List<Point> intersections = new ArrayList<>();

        for (int y = 1; y < grid.maxY() - 1; y++) {
            for (int x = 1; x < grid.maxX() - 1; x++) {
                if (isIntersectionAt(x, y))
                    intersections.add(Point.of(x, y));
            }
        }

        return intersections;
    }

    private boolean isIntersectionAt(int x, int y) {
        return grid.get(x, y) == SCAFFOLD &&
                grid.get(x - 1, y) == SCAFFOLD &&
                grid.get(x + 1, y) == SCAFFOLD &&
                grid.get(x, y - 1) == SCAFFOLD &&
                grid.get(x, y + 1) == SCAFFOLD;
    }

    public static int alignment(List<Point> intersections) {
        return intersections.stream()
                .mapToInt(point -> point.x() * point.y())
                .sum();
    }

    public List<String> route() {
        final List<String> commands = new ArrayList<>();

        final Optional<Point> start = grid.findFirst(c -> c == ROBOT_UP);
        if (start.isEmpty())
            throw new IllegalStateException("No starting point for the robot found on this grid.");

        Point current = start.get();
        Point direction = Point.SOUTH;
        Point nextDirection = nextDirection(direction, current);
        while (nextDirection != null) {
            commands.add(forNextDirection(direction, nextDirection));
            direction = nextDirection;

            int count = 0;
            Point next = current.add(direction);
            while (grid.contains(next.x(), next.y()) && grid.get(next.x(), next.y()) == SCAFFOLD) {
                count++;
                current = next;
                next = next.add(direction);
            }
            commands.add(String.valueOf(count));

            nextDirection = nextDirection(direction, current);
        }

        return commands;
    }

    private String forNextDirection(Point direction, Point nextDirection) {
        if (direction.equals(Point.NORTH)) {
            if (nextDirection.equals(Point.WEST)) return RIGHT_COMMAND;
            return LEFT_COMMAND;
        } else if (direction.equals(Point.EAST)) {
            if (nextDirection.equals(Point.NORTH)) return RIGHT_COMMAND;
            return LEFT_COMMAND;
        }  else if (direction.equals(Point.SOUTH)) {
            if (nextDirection.equals(Point.EAST)) return RIGHT_COMMAND;
            return LEFT_COMMAND;
        }  else {
            if (nextDirection.equals(Point.SOUTH)) return RIGHT_COMMAND;
            return LEFT_COMMAND;
        }
    }

    private Point nextDirection(Point direction, Point current) {
        for (Point next : List.of(Point.NORTH, Point.EAST, Point.SOUTH, Point.WEST)) {
            // ignore the same and opposite directions
            if (next.x() == direction.x() || next.y() == direction.y())
                continue;

            final Point newPoint = current.add(next);
            if (grid.contains(newPoint.x(), newPoint.y()) && grid.get(newPoint.x(), newPoint.y()) == SCAFFOLD)
                return next;
        }

        return null;
    }
}
