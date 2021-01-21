package com.putoet.day15;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;
import com.putoet.resources.CSV;
import com.putoet.search.GenericSearch;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day15 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.flatList("/day15.txt", Long::parseLong);
        final Grid grid = asGrid(intCode);
        final GridSearch search = new GridSearch(grid);

        Optional<Point> oxygenSystem = part1(search);
        oxygenSystem.ifPresent(point -> part2(search, point));
    }

    private static Optional<Point> part1(GridSearch search) {
        final Optional<GenericSearch.Node<GridSearch.State>> node = GenericSearch.bfs(
                search.init(Point.ORIGIN),
                search::oxygenSystemFound,
                search::oxygenSystemSearch);
        if (node.isEmpty())
            return Optional.empty();

        System.out.println("Found oxygenSystem in " + node.get().steps() + " steps.");

        return Optional.of(node.get().state.point);
    }

    private static void part2(GridSearch search, Point oxygenSystemLocation) {
        final List<GenericSearch.Node<GridSearch.State>> walls = GenericSearch.findAll(
                search.init(oxygenSystemLocation),
                search::wallFound,
                search::wallSearch);

        final GenericSearch.Node<GridSearch.State> longest = walls.stream().max(Comparator.comparingInt(GenericSearch.Node::steps)).get();
        System.out.println("The longest route has " + (longest.steps() - 1) + " steps");
    }

    private static Grid asGrid(List<Long> intCode) {
        final List<GenericSearch.Node<RepairDroid>> walls = GenericSearch.findAll(
                RepairDroid.init(intCode, Point.ORIGIN),
                RepairDroid::wallOrOxygenSystemFound,
                RepairDroid::successorsWalls);

        final Grid grid = createGrid(walls);

        walls.stream()
                .map(node -> node.state)
                .forEach(state -> grid.set(state.location().x, state.location().y, switch (state.result()) {
                            case MOVED -> GridSearch.OPEN;
                            case OXYGEN_SYSTEM_FOUND -> GridSearch.OXYGEN_SYSTEM;
                            case WALL_HIT -> GridSearch.WALL;
                        })
                );

        return grid;
    }

    private static Grid createGrid(List<GenericSearch.Node<RepairDroid>> walls) {
        final List<Point> wallPoints = walls.stream().map(node -> node.state.location()).collect(Collectors.toList());
        final int minX = wallPoints.stream().mapToInt(point -> point.x).min().getAsInt();
        final int maxX = wallPoints.stream().mapToInt(point -> point.x).max().getAsInt() + 1;
        final int minY = wallPoints.stream().mapToInt(point -> point.y).min().getAsInt();
        final int maxY = wallPoints.stream().mapToInt(point -> point.y).max().getAsInt() + 1;

        final int height = Math.abs(maxY - minY);
        final int width = Math.abs(maxX - minX);
        final char[][] data = new char[height][width];

        for (char[] datum : data) Arrays.fill(datum, GridSearch.OPEN);

        return new Grid(minX, maxX, minY, maxY, data);
    }
}
