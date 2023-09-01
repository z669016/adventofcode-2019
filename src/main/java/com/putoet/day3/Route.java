package com.putoet.day3;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Route {
    private final List<Point> route = new ArrayList<>(155_000);
    private Point endPoint = Point.ORIGIN;

    public Route() {}

    public Route(@NotNull List<String> pathList) {
        add(pathList.stream().map(Path::of).collect(Collectors.toList()));
    }

    private void add(@NotNull List<Path> paths) {
        paths.forEach(this::add);
    }

    private void add(@NotNull Path path) {
        if (path.length() > 0) {
            var coord = endPoint();
            for (int idx = 0; idx < path.length(); idx++) {
                coord = coord.add(path.direction().asMove());
                route.add(coord);
            }
            endPoint = coord;
        }
    }

    public Point endPoint() {
        return endPoint;
    }

    public int length() {
        return route.size();
    }

    public int manhattenDistance() {
        return endPoint.manhattanDistance(Point.ORIGIN);
    }

    public Set<Point> intersect(@NotNull Route other) {
        return route.stream().filter(other.route::contains).collect(Collectors.toSet());
    }

    public int stepsTo(@NotNull Point point) {
        final var idx = route.indexOf(point);
        if (idx == -1)
            throw new IllegalArgumentException("Coordinate not on route");

        return idx + 1;
    }

    @Override
    public String toString() {
        return route.toString();
    }
}
