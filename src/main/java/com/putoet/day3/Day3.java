package com.putoet.day3;

import com.putoet.resources.CSV;

import java.util.*;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        final List<List<String>> pathLists = CSV.list("/day3.txt");

        final Route route1 = new Route(pathLists.get(0));
        final Route route2 = new Route(pathLists.get(1));

        final long start = System.currentTimeMillis();
        final List<Coordinate> coordinates = route1.intersect(route2);
        System.out.printf("Route 1 contains %d steps and route 2 contains %d steps\n", route1.length(), route2.length());
        System.out.printf("Found %d intersections\n", coordinates.size());
        System.out.printf("Closest intersection distance is %d\n",
                coordinates.stream().min(Comparator.comparing(Coordinate::manhattanDistance)).get().manhattanDistance());
        System.out.printf("Solving the puzzle 3 took %dms\n", System.currentTimeMillis() - start);

        final List<Tuple<Coordinate, Integer>> tuples = coordinates.stream().map(c -> new Tuple<>(c, route1.stepsTo(c) + route2.stepsTo(c))).collect(Collectors.toList());
        // System.out.println(tuples);
        final Optional<Tuple<Coordinate, Integer>> closest = tuples.stream().min(Comparator.comparing(Tuple::y));
        System.out.printf("Closest intersection is %s at %d steps distance\n", closest.get().x(), closest.get().y());

    }
}

class Tuple<X, Y> {
    private final X x;
    private final Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X x() {
        return x;
    }

    public Y y() {
        return y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

class Coordinate {
    private final int x, y;
    public static final Coordinate ORIGIN = of(0, 0);

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    public int manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    public Coordinate move(Direction direction) {
        return switch (direction) {
            case UP -> of(this.x, this.y + 1);
            case DOWN -> of(this.x, this.y - 1);
            case LEFT -> of(this.x - 1, this.y);
            default -> of(this.x + 1, this.y);
        };
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction of(Character c) {
        if (c == null)
            throw new IllegalArgumentException("Not a direction");

        return switch (c) {
            case 'U' -> UP;
            case 'D' -> DOWN;
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalArgumentException("Not a direction: " + c);
        };
    }
}

class Path {
    private final Direction direction;
    private final int length;

    private Path(Direction direction, int length) {
        this.direction = direction;
        this.length = length;
    }

    public static Path of(String path) {
        if (path == null || path.length() == 0)
            throw new IllegalArgumentException("No path");

        try {
            final Direction direction = Direction.of(path.toUpperCase().charAt(0));
            final int length = (path.length() > 1) ? Integer.parseInt(path.substring(1)) : 0;

            if (length < 0)
                throw new IllegalArgumentException("Invalid length on path: " + path);

            return new Path(direction, length);
        } catch (NumberFormatException exc) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }
    }

    public static List<Path> of(String... paths) {
        return Arrays.stream(paths).map(Path::of).collect(Collectors.toList());
    }

    int length() {
        return length;
    }

    Direction direction() {
        return direction;
    }

    @Override
    public String toString() {
        return String.format("%s %d", direction, length);
    }
}

class Route {
    private final List<Coordinate> route = new ArrayList<>(175_000);

    public Route() {}

    public Route(List<String> pathList) {
        if (pathList == null)
            throw new IllegalArgumentException("No path list");

        add(pathList.stream().map(Path::of).collect(Collectors.toList()));
    }

    public void add(List<Path> paths) {
        paths.forEach(this::add);
    }

    public void add(Path path) {
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
        return route.stream().filter(other.route::contains).collect(Collectors.toList());
    }

    public int stepsTo(Coordinate coordinate) {
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