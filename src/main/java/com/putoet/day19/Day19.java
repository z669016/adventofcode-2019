package com.putoet.day19;

import com.putoet.resources.CSV;

import java.util.List;

public class Day19 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.flatList("/day19.txt", Long::parseLong);
        final TractorBeamMap tractorBeamMap = part1(intCode);
        part2(tractorBeamMap);
    }

    private static TractorBeamMap part1(List<Long> intCode) {
        final Drone drone = new Drone(intCode);
        final TractorBeamMap tractorBeamMap = TractorBeamMap.of(drone, 50, 50);

        System.out.println("The number of points affected by the tractor beam is " + tractorBeamMap.affectedPoints());

        return tractorBeamMap;
    }

    private static void part2(TractorBeamMap tractorBeamMap) {

    }
}
