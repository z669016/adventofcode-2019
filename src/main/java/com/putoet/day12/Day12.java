package com.putoet.day12;

import com.putoet.utils.Timer;

import java.util.HashSet;
import java.util.Map;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.util.ArithmeticUtils;

public class Day12 {
    public static void main(String[] args) {
        Timer.run(Day12::part1);
        Timer.run(Day12::part2);
    }

    private static void part1() {
        final var moons = MoonMap.loadFile("/day12.txt");

        for (var idx = 0; idx < 1000; idx++) {
            moons.values().forEach(moon -> moon.applyGravity(moons));
            moons.values().forEach(Moon::applyVelocity);
        }

        moons.values().forEach(System.out::println);
        System.out.println();
        System.out.println("Total energy in the  system is: " + moons.values().stream().mapToInt(Moon::totalEnergy).sum());
    }

    private static void part2() {
        final var moons = MoonMap.loadFile("/day12.txt");
        System.out.println("Repeat after " + repeat(moons) + " turns.");
    }

    static long repeat(Map<String, Moon> moons) {
        final var xHistory = new HashSet<String>();
        final var yHistory = new HashSet<String>();
        final var zHistory = new HashSet<String>();

        var repeatX = OptionalLong.empty();
        var repeatY = OptionalLong.empty();
        var repeatZ = OptionalLong.empty();

        xHistory.add(hashX(moons));
        yHistory.add(hashY(moons));
        zHistory.add(hashZ(moons));

        var idx = 0;
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

        return Stream.of(repeatX.getAsLong(), repeatY.getAsLong(), repeatZ.getAsLong())
                .reduce(1L, (x, y) -> x * (y / ArithmeticUtils.gcd(x, y)));
    }

    static String hashX(Map<String, Moon> moons) {
        return moons.values().stream()
                .map(moon -> moon.position().x() + "_" + moon.velocity().x())
                .collect(Collectors.joining("x"));
    }

    static String hashY(Map<String, Moon> moons) {
        return moons.values().stream()
                .map(moon -> moon.position().y() + "_" + moon.velocity().y())
                .collect(Collectors.joining("y"));
    }

    static String hashZ(Map<String, Moon> moons) {
        return moons.values().stream()
                .map(moon -> moon.position().z() + "_" + moon.velocity().z())
                .collect(Collectors.joining("z"));
    }
}
