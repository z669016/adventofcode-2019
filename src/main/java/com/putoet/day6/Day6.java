package com.putoet.day6;

import com.putoet.resources.ResourceLines;

public class Day6 {
    public static void main(String[] args) {
        final SpaceMap map = SpaceMapLoader.loadMap(ResourceLines.list("/day6.txt"));
        part1(map);
        part2(map);
    }

    private static void part1(SpaceMap map) {
        System.out.println("Orbit count for the spacemap is " + map.orbits());
    }

    private static void part2(SpaceMap map) {
        System.out.println("The distance from YOU to SAN is " + map.distance(map.get("YOU"), map.get("SAN")));
    }

}
