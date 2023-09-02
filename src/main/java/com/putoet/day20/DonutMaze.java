package com.putoet.day20;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class DonutMaze {
    public static final String ENTRY = "AA";
    public static final String EXIT = "ZZ";

    private final Grid grid;
    private final Map<String, Pair<Point, Point>> labels;

    public DonutMaze(@NotNull Grid grid) {
        this.grid = grid;
        labels = collectLabels(grid);
    }

    private static Map<String, Pair<Point, Point>> collectLabels(Grid grid) {
        final var labels = new TreeMap<String, Pair<Point, Point>>();

        for (var y = 0; y < grid.maxY(); y++) {
            for (var x = 0; x < grid.maxX(); x++) {
                if (Character.isLetter(grid.get(x, y))) {
                    final var label = labelAt(grid, Point.of(x, y));
                    final var point = entryForLabelAt(grid, Point.of(x, y));
                    if (!labels.containsKey(label))
                        labels.put(label, new Pair<>(point, null));
                    else
                        labels.put(label, labels.get(label).setAt1(point));
                }
            }
        }

        return labels;
    }

    private static String labelAt(Grid grid, Point point) {
        for (var adjacent : adjacent(point)) {
            if (grid.contains(adjacent.x(), adjacent.y())) {
                final var token = grid.get(adjacent.x(), adjacent.y());
                if (Character.isLetter(token)) {
                    return String.valueOf(grid.get(point.x(), point.y())) + token;
                }
            }
        }

        throw new IllegalStateException("No 2 character label found at " + point);
    }

    private static Point entryForLabelAt(Grid grid, Point point) {
        final var adjacentList = adjacent(point);
        for (var adjacent : adjacentList) {
            if (grid.contains(adjacent.x(), adjacent.y())) {
                final var token = grid.get(adjacent.x(), adjacent.y());
                if (token == '.')
                    return adjacent;
            }
        }

        for (var adjacent : adjacentList) {
            if (grid.contains(adjacent.x(), adjacent.y())) {
                final var token = grid.get(adjacent.x(), adjacent.y());
                if (Character.isLetter(token)) {
                    return entryForLabelAt(grid, adjacent);
                }
            }
        }

        throw new IllegalStateException("No 2 character label with an maze entry found at " + point);
    }

    public static List<Point> adjacent(Point point) {
        return List.of(
                point.add(Point.NORTH),
                point.add(Point.EAST),
                point.add(Point.SOUTH),
                point.add(Point.WEST)
        );
    }

    public char tokenAt(Point point) {
        assert point != null;

        return grid.get(point.x(), point.y());
    }

    public String labelAt(Point point) {
        assert point != null;

        return labelAt(grid, point);
    }

    public Pair<Point,Point> gate(String label) {
        assert label != null;

        return labels.get(label);
    }

    public Point entry() {
        return labels.get(ENTRY).getValue0();
    }

    public Point exit() {
        return labels.get(EXIT).getValue0();
    }

    public int maxX() {
        return grid.maxX();
    }

    public int maxY() {
        return grid.maxY();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(grid.toString());
        sb.append("\n");
        labels.forEach((key, value) -> sb.append(key)
                .append(" from ")
                .append(value.getValue0())
                .append(" to ")
                .append(value.getValue1())
                .append("\n")
        );

        return sb.toString();
    }
}
