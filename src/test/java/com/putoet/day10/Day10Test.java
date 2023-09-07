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
        final var lineOfSightMaps = spaceArea.linesOfSightMaps();
        assertEquals(33, lineOfSightMaps.values().stream().mapToInt(LineOfSightMap::inLineOfSightCount).max().orElseThrow());
        assertEquals("A0508", lineOfSightMaps.entrySet().stream()
                .filter(entry -> entry.getValue().inLineOfSightCount() == 33)
                .findFirst()
                .orElseThrow()
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
        final var lineOfSightMaps = spaceArea.linesOfSightMaps();
        assertEquals(210, lineOfSightMaps.values().stream().mapToInt(LineOfSightMap::inLineOfSightCount).max().orElseThrow());
        assertEquals("A1113", lineOfSightMaps.entrySet().stream()
                .filter(entry -> entry.getValue().inLineOfSightCount() == 210)
                .findFirst()
                .orElseThrow()
                .getKey()
                .name());
    }

    @Test
    public void test3() {
        final var spaceArea = SpaceArea.of(List.of(
                ".#....#####...#..",
                "##...##.#####..##",
                "##...#...#.#####.",
                "..#.....#...###..",
                "..#.#.....#....##"));
        final var asteroid = spaceArea.asteroidAt(Point.of(8, 3));
        final var linesOfSightMap = spaceArea.linesOfSightMapFor(asteroid.orElseThrow());
        final var linesOfSight = linesOfSightMap.orElseThrow().map();

        System.out.println(linesOfSight);

        var iter = linesOfSight.iterator();
        var result = iter.next().vaporize();
        while (result.isPresent()) {
            if (!iter.hasNext())
                iter = linesOfSight.iterator();

            result = iter.next().vaporize();
        }
    }
}
