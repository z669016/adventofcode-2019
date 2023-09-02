package com.putoet.day19;

import com.putoet.grid.Point;
import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.List;

public class Day19 {
    public static void main(String[] args) {
        final var intCode = CSV.flatList("/day19.txt", Long::parseLong);

        Timer.run(() -> part1(intCode));
        Timer.run(() -> part2(intCode));
    }

    private static void part1(List<Long> intCode) {
        final var drone = new Drone(intCode);
        final var tractorBeamMap = TractorBeamMap.of(drone, 50, 50);

        System.out.println("The number of points affected by the tractor beam is " + tractorBeamMap.affectedPoints());
    }

    private static void part2(List<Long> intCode) {
        final var drone = new Drone(intCode);
        final var search = new TractorBeamSearch(drone, y -> (int)(1.1 * y), y -> (int)(1.4 * y));
        final var topLeft = search.squareTopLeft(100);
        System.out.println("The answer code is " + (topLeft.x() * 10_000 + topLeft.y()));
    }
}
