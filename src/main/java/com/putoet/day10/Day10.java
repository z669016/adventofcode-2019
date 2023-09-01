package com.putoet.day10;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.Map;
import java.util.Optional;

public class Day10 {
    public static void main(String[] args) {
        final var spaceArea = SpaceArea.of(ResourceLines.list("/day10.txt"));
        final var lineOfSightMaps = spaceArea.linesOfSightMaps();
        final var max = lineOfSightMaps.values().stream()
                .mapToInt(LineOfSightMap::inLineOfSightCount)
                .max()
                .orElseThrow();

        final var asteroid = Timer.run(() -> part1(lineOfSightMaps, max));
        Timer.run(() -> part2(lineOfSightMaps, asteroid));
    }

    private static Asteroid part1(Map<Asteroid, LineOfSightMap> lineOfSightMaps, int max) {
        final var asteroid = lineOfSightMaps.entrySet().stream()
                .filter(entry -> entry.getValue().inLineOfSightCount() == max)
                .findFirst()
                .orElseThrow()
                .getKey();

        System.out.printf("The best location for the monitoring station is at asteroid %s with %d asteroids in its direct line of sight%n", asteroid, max);
        return asteroid;
    }

    private static void part2(Map<Asteroid, LineOfSightMap> lineOfSightMaps, Asteroid asteroid) {
        final var monitoringAsteroid = lineOfSightMaps.get(asteroid);
        final var linesOfSight = monitoringAsteroid.map();

        var iter = linesOfSight.iterator();
        Optional<Asteroid> result = Optional.empty();
        var count = 0;
        while (count < 200) {
            if (!iter.hasNext())
                iter = linesOfSight.iterator();

            result = iter.next().vaporize();
            if (result.isPresent())
                count++;
        }

        System.out.println("200th asteroid vaporized was " + result.get());
    }
}
