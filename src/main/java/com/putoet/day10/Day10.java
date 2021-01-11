package com.putoet.day10;

import com.putoet.resources.ResourceLines;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Day10 {
    public static void main(String[] args) {
        final SpaceArea spaceArea = SpaceArea.of(ResourceLines.list("/day10.txt"));
        final Map<Asteroid, LineOfSightMap> lineOfSightMaps = spaceArea.linesOfSightMaps();
        final int max = lineOfSightMaps.values().stream().mapToInt(LineOfSightMap::inLineOfSightCount).max().getAsInt();

        final Asteroid asteroid = part1(lineOfSightMaps, max);
        part2(lineOfSightMaps, asteroid);
    }

    private static Asteroid part1(Map<Asteroid, LineOfSightMap> lineOfSightMaps, int max) {
        final Asteroid asteroid = lineOfSightMaps.entrySet().stream()
                .filter(entry -> entry.getValue().inLineOfSightCount() == max)
                .findFirst()
                .get()
                .getKey();

        System.out.printf("The best location for the monitoring station is at asteroid %s with %d asteroids in its direct line of sight%n", asteroid, max);
        return asteroid;
    }

    private static void part2(Map<Asteroid, LineOfSightMap> lineOfSightMaps, Asteroid asteroid) {
        final LineOfSightMap monitoringAsteroid = lineOfSightMaps.get(asteroid);
        final Set<LineOfSight> linesOfSight = monitoringAsteroid.map();

        Iterator<LineOfSight> iter = linesOfSight.iterator();
        Optional<Asteroid> result = Optional.empty();
        int count = 0;
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
