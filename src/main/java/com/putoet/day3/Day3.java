package com.putoet.day3;

import com.putoet.resources.CSV;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

