package com.putoet.day12;

import com.putoet.math.EuclideanAlgorithm;

import java.util.*;
import java.util.stream.Collectors;

public class Day12 {
    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        final Map<String, Moon> moons = MoonMap.loadFile("/day12.txt");

        for (int idx = 0; idx < 1000; idx++) {
            moons.values().forEach(moon -> moon.applyGravity(moons));
            moons.values().forEach(Moon::applyVelocity);
        }

        moons.values().forEach(System.out::println);
        System.out.println();
        System.out.println("Total energy in the  system is: " + moons.values().stream().mapToInt(Moon::totalEnergy).sum());
    }

    private static void part2() {
        final Map<String, Moon> moons = MoonMap.loadFile("/day12.txt");
        System.out.println("Repeat after " + repeat(moons) + " turns.");
    }

    public static long repeat(Map<String, Moon> moons) {
        final Set<String> xHistory = new HashSet<>();
        final Set<String> yHistory = new HashSet<>();
        final Set<String> zHistory = new HashSet<>();

        OptionalLong repeatX = OptionalLong.empty();
        OptionalLong repeatY = OptionalLong.empty();
        OptionalLong repeatZ = OptionalLong.empty();

        xHistory.add(hashX(moons));
        yHistory.add(hashY(moons));
        zHistory.add(hashZ(moons));

        long idx = 0;
        do {
            moons.values().forEach(moon -> moon.applyGravity(moons));
            moons.values().forEach(Moon::applyVelocity);
            idx++;

            if (repeatX.isEmpty() && !xHistory.add(hashX(moons)))
                repeatX = OptionalLong.of(idx);

            if (repeatY.isEmpty() && !yHistory.add(hashY(moons)))
                repeatY = OptionalLong.of(idx);

            if (repeatZ.isEmpty() && !zHistory.add(hashZ(moons)))
                repeatZ = OptionalLong.of(idx);

        } while (repeatX.isEmpty() || repeatY.isEmpty() || repeatZ.isEmpty());

        return EuclideanAlgorithm.lcm(List.of(repeatX.getAsLong(), repeatY.getAsLong(), repeatZ.getAsLong()));
    }

    private static String hashX(Map<String,Moon> moons) {
        return moons.values().stream()
                .map(moon -> moon.position().x + "_" + moon.velocity().x)
                .collect(Collectors.joining("x"));
    }

    private static String hashY(Map<String,Moon> moons) {
        return moons.values().stream()
                .map(moon -> moon.position().y + "_" + moon.velocity().y)
                .collect(Collectors.joining("y"));
    }

    private static String hashZ(Map<String,Moon> moons) {
        return moons.values().stream()
                .map(moon -> moon.position().z + "_" + moon.velocity().z)
                .collect(Collectors.joining("z"));
    }
}
