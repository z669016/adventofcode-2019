package com.putoet.day3;

import com.putoet.grid.Point;
import com.putoet.resources.CSV;
import com.putoet.utils.Timer;
import org.javatuples.Pair;

import java.util.Comparator;
import java.util.Set;

public class Day3 {
    public static void main(String[] args) {
        final var pathLists = CSV.list("/day3.txt");
        final var route1 = new Route(pathLists.get(0));
        final var route2 = new Route(pathLists.get(1));
        final var intersections = Timer.run(() -> intersectiions(route1, route2));

        Timer.run(() -> part1(intersections));
        Timer.run(() -> part2(route1, route2, intersections));
    }

    private static Set<Point> intersectiions(Route route1, Route route2) {
        final var intersections = route1.intersect(route2);
        System.out.printf("Route 1 contains %d steps and route 2 contains %d steps\n", route1.length(), route2.length());
        System.out.printf("Found %d intersections\n", intersections.size());
        return intersections;
    }

    private static void part1(Set<Point> intersections) {
        System.out.printf("Closest intersection distance is %d\n",
                intersections.stream()
                        .min(Comparator.comparing(Point::manhattanDistance))
                        .orElseThrow()
                        .manhattanDistance());
    }

    private static void part2(Route route1, Route route2, Set<Point> intersections) {
        final var pairs = intersections.stream().map(c -> new Pair<>(c, route1.stepsTo(c) + route2.stepsTo(c))).toList();
        final var closest = pairs.stream().min(Comparator.comparing(Pair::getValue1));
        System.out.printf("Closest intersection is %s at %d steps distance\n",
                closest.orElseThrow().getValue0(), closest.orElseThrow().getValue1());
    }
}

