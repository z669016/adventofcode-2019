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

        final Optional<GenericSearch.Node<RepairDroid>> node = part1(intCode);
        node.ifPresent(state -> part2(intCode, state.state.location(), node.get()));
    }

    private static Optional<GenericSearch.Node<RepairDroid>> part1(List<Long> intCode) {
        final Optional<GenericSearch.Node<RepairDroid>> node = GenericSearch.bfs(
                RepairDroid.init(intCode, Point.ORIGIN),
                RepairDroid::oxygenSystemFound,
                RepairDroid::successorsMoved);

        System.out.println();
        if (node.isEmpty()) {
            System.out.println("Oxygen system not found.");
            return Optional.empty();
        }

        System.out.println("Oxygen system found at " + node.get().state.location());
        System.out.println("It took " + node.get().steps() + " to get there.");

        return node;
    }

    private static void part2(List<Long> intCode, Point oxygenSystemLocation, GenericSearch.Node<RepairDroid> path) {
        System.out.println("Finding the longest possible route from " + oxygenSystemLocation + " to the borders");

        final Grid grid = asGrid(oxygenSystemLocation, intCode);
        final GridSearch search = new GridSearch(grid, oxygenSystemLocation);
        final List<GenericSearch.Node<GridSearch.State>> walls = GenericSearch.all(
                search.init(),
                search::hitWall,
                search::successors);

        final GenericSearch.Node<GridSearch.State> longest = walls.stream().max(Comparator.comparingInt(GenericSearch.Node::steps)).get();
        System.out.println("The longest route has " + (longest.steps() - 1) + " steps");
    }

    private static Grid asGrid(Point oxygenSystemLocation,
                               List<Long> intCode) {
        final List<GenericSearch.Node<RepairDroid>> walls = GenericSearch.all(
                RepairDroid.init(intCode, Point.ORIGIN),
                RepairDroid::wallFound,
                RepairDroid::successorsWalls);

        final Grid grid = createGrid(walls);

        walls.stream()
                .filter(node -> node.state.result() == RepairDroid.MovementSensor.WALL_HIT)
                .map(node -> node.state.location())
                .forEach(point -> grid.set(point.x, point.y, '#'));

        grid.set(oxygenSystemLocation.x, oxygenSystemLocation.y, 'O');

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

        for (char[] datum : data) Arrays.fill(datum, '.');

        final Grid grid = new Grid(minX, maxX, minY, maxY, data);
        return grid;
    }

    private List<Point> successors(Point from) {
        return List.of(from.add(Point.NORTH),
                from.add(Point.SOUTH),
                from.add(Point.WEST),
                from.add(Point.EAST));
    }
}
