package com.putoet.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Route {
    private final List<Coordinate> route = new ArrayList<>(175_000);

    public Route() {
    }

    public Route(List<String> pathList) {
        assert pathList != null;

        add(pathList.stream().map(Path::of).collect(Collectors.toList()));
    }

    public void add(List<Path> paths) {
        assert paths != null;

        paths.forEach(this::add);
    }

    public void add(Path path) {
        assert path != null;

        if (path.length() > 0) {
            Coordinate coord = endPoint();
            for (int idx = 0; idx < path.length(); idx++) {
                coord = coord.move(path.direction());
                route.add(coord);
            }
        }
    }

    public Coordinate endPoint() {
        return route.size() > 0 ? route.get(route.size() - 1) : Coordinate.ORIGIN;
    }

    public int length() {
        return route.size();
    }

    public int distanceToOrigin() {
        return endPoint().manhattanDistance();
    }

    public List<Coordinate> intersect(Route other) {
        assert other != null;

        return route.stream().filter(other.route::contains).collect(Collectors.toList());
    }

    public int stepsTo(Coordinate coordinate) {
        assert coordinate != null;

        final int idx = route.indexOf(coordinate);
        if (idx == -1)
            throw new IllegalArgumentException("Coordinate not on route");

        return idx + 1;
    }

    @Override
    public String toString() {
        return route.toString();
    }
}
