package com.putoet.day10;

import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class Day10Test {
    @Test
    public void test1() {
        final AstroidMap astroidMap = AstroidMap.of(new String[]{
                "......#.#.",
                "#..#.#....",
                "..#######.",
                ".#.#.###..",
                ".#..#.....",
                "..#....#.#",
                "#..#....#.",
                ".##.#..###",
                "##...#..#.",
                ".#....####"});
        final Map<Astroid, LineOfSightMap> lineOfSightMaps = astroidMap.linesOfSightMaps();
        assertEquals(33, lineOfSightMaps.entrySet().stream().mapToInt(entry -> entry.getValue().inLineOfSightCount()).max().getAsInt());
        assertEquals("A0508", lineOfSightMaps.entrySet().stream()
                .filter(entry -> entry.getValue().inLineOfSightCount() == 33)
                .findFirst()
                .get()
                .getKey()
                .name());
    }

    @Test
    public void test2() {
        final AstroidMap astroidMap = AstroidMap.of(new String[]{
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
                "###.##.####.##.#..##"});
        final Map<Astroid, LineOfSightMap> lineOfSightMaps = astroidMap.linesOfSightMaps();
        assertEquals(210, lineOfSightMaps.entrySet().stream().mapToInt(entry -> entry.getValue().inLineOfSightCount()).max().getAsInt());
        assertEquals("A1113", lineOfSightMaps.entrySet().stream()
                .filter(entry -> entry.getValue().inLineOfSightCount() == 210)
                .findFirst()
                .get()
                .getKey()
                .name());
    }

}
