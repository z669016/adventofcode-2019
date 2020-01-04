package com.putoet.day12;

import java.util.Map;

public class Day12a {
    public static void main(String[] args) {
        final Map<String,Moon> moons = MoonMap.loadFile("day12.txt");

        for (int idx = 0; idx < 1000; idx++) {
            moons.values().forEach(moon -> moon.applyGravity(moons));
            moons.values().forEach(Moon::applyVelocity);
        }

        moons.values().forEach(System.out::println);
        System.out.println();
        System.out.println("Total energy in the  system is: " + moons.values().stream().mapToInt(Moon::totalEnergy).sum());
    }
}
