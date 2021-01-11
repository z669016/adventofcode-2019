package com.putoet.day10;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {
    @Test
    public void test1() {
        final SpaceArea spaceArea = SpaceArea.of(List.of(
                "......#.#.",
                "#..#.#....",
                "..#######.",
                ".#.#.###..",
                ".#..#.....",
                "..#....#.#",
                "#..#....#.",
                ".##.#..###",
                "##...#..#.",
                ".#....####"));
        final Map<Asteroid, LineOfSightMap> lineOfSightMaps = spaceArea.linesOfSightMaps();
        assertEquals(33, lineOfSightMaps.values().stream().mapToInt(LineOfSightMap::inLineOfSightCount).max().getAsInt());
        assertEquals("A0508", lineOfSightMaps.entrySet().stream()
                .filter(entry -> entry.getValue().inLineOfSightCount() == 33)
                .findFirst()
                .get()
                .getKey()
                .name());
    }

    @Test
    public void test2() {
        final SpaceArea spaceArea = SpaceArea.of(List.of(
                ".#..##.###...#######",
                "##.############..##.",
                ".#.######.########.#",
                ".###.#######.####.#.",
                "#####.##.#.##.###.##",
                "..#####..#.#########",
                "####################",
                "#.####....###.#.#.##",
                "##.#################",
                "#####.##.###..####..",
                "..######..##.#######",
                "####.##.####...##..#",
                ".#####..#.######.###",
                "##...#.##########...",
                "#.##########.#######",
                ".####.#.###.###.#.##",
                "....##.##.###..#####",
                ".#.#.###########.###",
                "#.#.#.#####.####.###",
                "###.##.####.##.#..##"));
        final Map<Asteroid, LineOfSightMap> lineOfSightMaps = spaceArea.linesOfSightMaps();
        assertEquals(210, lineOfSightMaps.values().stream().mapToInt(LineOfSightMap::inLineOfSightCount).max().getAsInt());
        assertEquals("A1113", lineOfSightMaps.entrySet().stream()
                .filter(entry -> entry.getValue().inLineOfSightCount() == 210)
                .findFirst()
                .get()
                .getKey()
                .name());
    }

    @Test
    public void test3() {
        final SpaceArea spaceArea = SpaceArea.of(List.of(
                ".#....#####...#..",
                "##...##.#####..##",
                "##...#...#.#####.",
                "..#.....#...###..",
                "..#.#.....#....##"));
        final Optional<Asteroid> asteroid = spaceArea.asteroidAt(Point.of(8, 3));
        final Optional<LineOfSightMap> linesOfSightMap = spaceArea.linesOfSightMapFor(asteroid.get());
        final Set<LineOfSight> linesOfSight = linesOfSightMap.get().map();

        System.out.println(linesOfSight);

        Iterator<LineOfSight> iter = linesOfSight.iterator();
        Optional<Asteroid> result = iter.next().vaporize();
        while (result.isPresent()) {
            if (!iter.hasNext())
                iter = linesOfSight.iterator();

            result = iter.next().vaporize();
        }
    }
}
