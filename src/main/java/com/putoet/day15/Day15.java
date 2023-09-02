package com.putoet.day15;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;
import com.putoet.resources.CSV;
import com.putoet.search.GenericSearch;
import com.putoet.utils.Timer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Day15 {
    public static void main(String[] args) {
        final var intCode = CSV.flatList("/day15.txt", Long::parseLong);
        final var grid = asGrid(intCode);
        final var search = new GridSearch(grid);

        final var oxygenSystem = Timer.run(() -> part1(search));
        oxygenSystem.ifPresent(point -> Timer.run(() -> part2(search, point)));
    }

    private static Optional<Point> part1(GridSearch search) {
        final var node = GenericSearch.bfs(
                search.init(Point.ORIGIN),
                search::oxygenSystemFound,
                search::oxygenSystemSearch);
        if (node.isEmpty())
            return Optional.empty();

        System.out.println("Found oxygenSystem in " + node.get().steps() + " steps.");

        return Optional.of(node.get().state.point());
    }

    private static void part2(GridSearch search, Point oxygenSystemLocation) {
        final var walls = GenericSearch.findAll(
                search.init(oxygenSystemLocation),
                search::wallFound,
                search::wallSearch);

        final var longest = walls.stream()
                .max(Comparator.comparingInt(GenericSearch.Node::steps))
                .orElseThrow();
        System.out.println("The longest route has " + (longest.steps() - 1) + " steps");
    }

    private static Grid asGrid(List<Long> intCode) {
        final var walls = GenericSearch.findAll(
                RepairDroid.init(intCode, Point.ORIGIN),
                RepairDroid::wallOrOxygenSystemFound,
                RepairDroid::successorsWalls);

        final var grid = createGrid(walls);

        walls.stream()
                .map(node -> node.state)
                .forEach(state -> grid.set(state.location().x(), state.location().y(), switch (state.result()) {
                            case MOVED -> GridSearch.OPEN;
                            case OXYGEN_SYSTEM_FOUND -> GridSearch.OXYGEN_SYSTEM;
                            case WALL_HIT -> GridSearch.WALL;
                        })
                );

        return grid;
    }

    private static Grid createGrid(List<GenericSearch.Node<RepairDroid>> walls) {
        final var wallPoints = walls.stream().map(node -> node.state.location()).toList();
        final var minX = wallPoints.stream().mapToInt(Point::x).min().orElseThrow();
        final var maxX = wallPoints.stream().mapToInt(Point::x).max().orElseThrow() + 1;
        final var minY = wallPoints.stream().mapToInt(Point::y).min().orElseThrow();
        final var maxY = wallPoints.stream().mapToInt(Point::y).max().orElseThrow() + 1;

        final var height = Math.abs(maxY - minY);
        final var width = Math.abs(maxX - minX);
        final var data = new char[height][width];

        for (var datum : data) Arrays.fill(datum, GridSearch.OPEN);

        return new Grid(minX, maxX, minY, maxY, data);
    }
}
