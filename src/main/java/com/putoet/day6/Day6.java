package com.putoet.day6;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day6 {
    public static void main(String[] args) {
        final var map = SpaceMapLoader.loadMap(ResourceLines.list("/day6.txt"));
        Timer.run(() -> System.out.println("Orbit count for the space map is " + map.orbits()));
        Timer.run(() -> System.out.println("The distance from YOU to SAN is " + map.distance(map.get("YOU"), map.get("SAN"))));
    }
}
