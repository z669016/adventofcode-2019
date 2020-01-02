package com.putoet.day10;

import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    @Test
    public void test3() {
        final AstroidMap astroidMap = AstroidMap.of(new String[]{
                ".#....#####...#..",
                "##...##.#####..##",
                "##...#...#.#####.",
                "..#.....#...###..",
                "..#.#.....#....##"});
        final Map<Astroid, LineOfSightMap> lineOfSightMaps = astroidMap.linesOfSightMaps();
        final Optional<Astroid> astroid = astroidMap.astroidAt(new Point(8,3));
        final Optional<LineOfSightMap> linesOfSightMap = astroidMap.linesOfSightMapFor(astroid.get());
        final Set<LineOfSight> linesOfSight = linesOfSightMap.get().map();

        System.out.println(linesOfSight);

        Iterator<LineOfSight> iter = linesOfSight.iterator();
        Optional<Astroid> result = iter.next().vaporize();
        while (result.isPresent()) {
            if (!iter.hasNext())
                iter = linesOfSight.iterator();

            result = iter.next().vaporize();
        }
    }
}
