package com.putoet.day24;

import com.putoet.grid.Grid;
import com.putoet.grid.GridUtils;
import com.putoet.grid.Point3D;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RecursiveFoldedEris {
    protected static final Grid EMPTY_GRID = new Grid(-2, 3, -2, 3,
            GridUtils.of(0, 5, 0, 5, '.'));

    private final Map<Integer, Grid> map;

    public RecursiveFoldedEris(char[][] grid) {
        assert grid != null && grid.length == 5 && grid[0].length == 5;

        map = new HashMap<>();

        final Grid zero = EMPTY_GRID.copy();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                zero.grid()[y][x] = grid[y][x];
            }
        }
        map.put(0, zero);
    }

    protected RecursiveFoldedEris(Map<Integer, Grid> map) {
        this.map = map;
    }

    private static List<Point3D> northUp(int level) {
        return List.of(Point3D.of(0, -1, level));
    }

    private static List<Point3D> westUp(int level) {
        return List.of(Point3D.of(-1, 0, level));
    }

    private static List<Point3D> eastUp(int level) {
        return List.of(Point3D.of(1, 0, level));
    }

    private static List<Point3D> southUp(int level) {
        return List.of(Point3D.of(0, 1, level));
    }

    private static List<Point3D> northDown(int level) {
        return IntStream.range(EMPTY_GRID.minX(), EMPTY_GRID.maxX())
                .mapToObj(x -> Point3D.of(x, EMPTY_GRID.minY(), level))
                .collect(Collectors.toList());
    }

    private static List<Point3D> westDown(int level) {
        return IntStream.range(EMPTY_GRID.minY(), EMPTY_GRID.maxY())
                .mapToObj(y -> Point3D.of(EMPTY_GRID.minX(), y, level))
                .collect(Collectors.toList());
    }

    private static List<Point3D> eastDown(int level) {
        return IntStream.range(EMPTY_GRID.minY(), EMPTY_GRID.maxY())
                .mapToObj(y -> Point3D.of(EMPTY_GRID.maxX() - 1, y, level))
                .collect(Collectors.toList());
    }

    private static List<Point3D> southDown(int level) {
        return IntStream.range(EMPTY_GRID.minX(), EMPTY_GRID.maxX())
                .mapToObj(x -> Point3D.of(x, EMPTY_GRID.maxY() - 1, level))
                .collect(Collectors.toList());
    }

    public RecursiveFoldedEris evolve() {
        final Map<Integer, Grid> evolve = new HashMap<>();
        int minKey = 0, maxKey = 0;
        for (Integer key : map.keySet()) {
            minKey = Math.min(minKey, key);
            maxKey = Math.max(maxKey, key);

            final Grid grid = map.get(key);
            final Grid evolvedGrid = grid.copy();
            evolve.put(key, evolvedGrid);

            evolveGrid(key, grid, evolvedGrid);
        }

        addLevelIfRequired(evolve, minKey - 1);
        addLevelIfRequired(evolve, maxKey + 1);

        return new RecursiveFoldedEris(evolve);
    }

    public void addLevelIfRequired(Map<Integer, Grid> evolve, int key) {
        final Grid grid = EMPTY_GRID.copy();
        evolveGrid(key, EMPTY_GRID, grid);
        if (grid.count(Eris.BUG) > 0)
            evolve.put(key, grid);
    }

    private void evolveGrid(Integer key, Grid grid, Grid evolvedGrid) {
        for (int y = grid.minY(); y < grid.maxY(); y++) {
            for (int x = grid.minX(); x < grid.maxX(); x++) {
                if (!(x == 0 && y == 0)) {
                    final Point3D current = Point3D.of(x, y, key);
                    final long adjacentBugCount = adjacentBugCount(adjacent(current));

                    if (grid.get(x, y) == Eris.BUG)
                        evolvedGrid.set(x, y, adjacentBugCount == 1L ? Eris.BUG : Eris.EMPTY);
                    else
                        evolvedGrid.set(x, y, adjacentBugCount == 1L || adjacentBugCount == 2L ? Eris.BUG : Eris.EMPTY);
                }
            }
        }
    }

    public List<Point3D> adjacent(Point3D current) {
        return Eris.NEIGHBOURS.stream()
                .map(neighbour -> Point3D.of(current.x() + neighbour.x(), current.y() + neighbour.y(), current.z()))
                .map(point3d -> {
                    final Grid grid = map.getOrDefault(point3d.z(), EMPTY_GRID);
                    if (!grid.contains(point3d.x(), point3d.y())) {
                        if (point3d.x() < grid.minX())
                            return westUp(point3d.z() - 1);
                        if (point3d.x() >= grid.maxX())
                            return eastUp(point3d.z() - 1);

                        if (point3d.y() < grid.minY())
                            return northUp(point3d.z() - 1);
                        if (point3d.y() >= grid.maxY())
                            return southUp(point3d.z() - 1);
                    }

                    if (point3d.x() == 0 && point3d.y() == 0) {
                        if (current.x() == 1)
                            return eastDown(point3d.z() + 1);
                        if (current.x() == -1)
                            return westDown(point3d.z() + 1);

                        if (current.y() == 1)
                            return southDown(point3d.z() + 1);
                        if (current.y() == -1)
                            return northDown(point3d.z() + 1);
                    }

                    return List.of(point3d);
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private long adjacentBugCount(List<Point3D> adjacent) {
        return adjacent.stream()
                .filter(point3D -> get(point3D) == Eris.BUG)
                .count();
    }

    private char get(Point3D point) {
        return map.getOrDefault(point.z(), EMPTY_GRID).get(point.x(), point.y());
    }

    public long bugs() {
        return map.values().stream()
                .mapToLong(grid -> grid.count(Eris.BUG))
                .sum();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        map.keySet().stream()
                .sorted()
                .forEach(key -> sb
                        .append("Level ")
                        .append(key)
                        .append("\n")
                        .append(map.get(key).toString())
                        .append("\n")
                );

        return sb.toString();
    }
}
