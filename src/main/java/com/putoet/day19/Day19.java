package com.putoet.day19;

import com.putoet.grid.Point;
import com.putoet.resources.CSV;

import java.util.List;

public class Day19 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.flatList("/day19.txt", Long::parseLong);

        part1(intCode);
        part2(intCode);
    }

    private static void part1(List<Long> intCode) {
        final Drone drone = new Drone(intCode);
        final TractorBeamMap tractorBeamMap = TractorBeamMap.of(drone, 50, 50);

        System.out.println("The number of points affected by the tractor beam is " + tractorBeamMap.affectedPoints());
        System.out.println("Lower bean for line 35 is " + tractorBeamMap.lowerVector(35));
        System.out.println("Upper bean for line 35 is " + tractorBeamMap.upperVector(35));
    }

    private static void part2(List<Long> intCode) {
        final Drone drone = new Drone(intCode);
        final TractorBeamSearch search = new TractorBeamSearch(drone, y -> (int)(1.1 * y), y -> (int)(1.4 * y));
        final Point topLeft = search.squareTopLeft(100);
        System.out.println("The top left position for the 100x100 square is " + topLeft);
        System.out.println("The answer code is " + (topLeft.x * 10_000 + topLeft.y));
    }
}
