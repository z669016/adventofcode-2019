package com.putoet.day3;

import com.putoet.grid.Point;
import com.putoet.resources.CSV;
import org.javatuples.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        final List<List<String>> pathLists = CSV.list("/day3.txt");

        final long start = System.currentTimeMillis();
        final Route route1 = new Route(pathLists.get(0));
        final Route route2 = new Route(pathLists.get(1));
        final Set<Point> intersection = route1.intersect(route2);
        System.out.printf("Route 1 contains %d steps and route 2 contains %d steps\n", route1.length(), route2.length());
        System.out.printf("Found %d intersections\n", intersection.size());
        System.out.printf("Gathering data took %dms\n", System.currentTimeMillis() - start);

        part1(intersection);
        part2(route1, route2, intersection);
    }

    private static void part1(Set<Point> intersection) {
        System.out.printf("Closest intersection distance is %d\n",
                intersection.stream().min(Comparator.comparing(Point::manhattanDistance)).get().manhattanDistance());
    }

    private static void part2(Route route1, Route route2, Set<Point> intersection) {
        final List<Pair<Point, Integer>> pairs = intersection.stream().map(c -> new Pair<>(c, route1.stepsTo(c) + route2.stepsTo(c))).collect(Collectors.toList());
        final Optional<Pair<Point, Integer>> closest = pairs.stream().min(Comparator.comparing(Pair::getValue1));
        System.out.printf("Closest intersection is %s at %d steps distance\n", closest.get().getValue0(), closest.get().getValue1());
    }
}

