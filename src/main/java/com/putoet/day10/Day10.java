package com.putoet.day10;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Day10 {
    public static void main(String[] args) {
        final AstroidMap astroidMap = AstroidMap.of(new String[]{
                "##.###.#.......#.#....#....#..........#.",
                "....#..#..#.....#.##.............#......",
                "...#.#..###..#..#.....#........#......#.",
                "#......#.....#.##.#.##.##...#...#......#",
                ".............#....#.....#.#......#.#....",
                "..##.....#..#..#.#.#....##.......#.....#",
                ".#........#...#...#.#.....#.....#.#..#.#",
                "...#...........#....#..#.#..#...##.#.#..",
                "#.##.#.#...#..#...........#..........#..",
                "........#.#..#..##.#.##......##.........",
                "................#.##.#....##.......#....",
                "#............#.........###...#...#.....#",
                "#....#..#....##.#....#...#.....#......#.",
                ".........#...#.#....#.#.....#...#...#...",
                ".............###.....#.#...##...........",
                "...#...#.......#....#.#...#....#...#....",
                ".....#..#...#.#.........##....#...#.....",
                "....##.........#......#...#...#....#..#.",
                "#...#..#..#.#...##.#..#.............#.##",
                ".....#...##..#....#.#.##..##.....#....#.",
                "..#....#..#........#.#.......#.##..###..",
                "...#....#..#.#.#........##..#..#..##....",
                ".......#.##.....#.#.....#...#...........",
                "........#.......#.#...........#..###..##",
                "...#.....#..#.#.......##.###.###...#....",
                "...............#..#....#.#....#....#.#..",
                "#......#...#.....#.#........##.##.#.....",
                "###.......#............#....#..#.#......",
                "..###.#.#....##..#.......#.............#",
                "##.#.#...#.#..........##.#..#...##......",
                "..#......#..........#.#..#....##........",
                "......##.##.#....#....#..........#...#..",
                "#.#..#..#.#...........#..#.......#..#.#.",
                "#.....#.#.........#............#.#..##.#",
                ".....##....#.##....#.....#..##....#..#..",
                ".#.......#......#.......#....#....#..#..",
                "...#........#.#.##..#.#..#..#........#..",
                "#........#.#......#..###....##..#......#",
                "...#....#...#.....#.....#.##.#..#...#...",
                "#.#.....##....#...........#.....#...#..."});
        final Map<Astroid, LineOfSightMap> lineOfSightMaps = astroidMap.linesOfSightMaps();
        final int max = lineOfSightMaps.entrySet().stream().mapToInt(entry -> entry.getValue().inLineOfSightCount()).max().getAsInt();
        final Astroid astroid = lineOfSightMaps.entrySet().stream()
                .filter(entry -> entry.getValue().inLineOfSightCount() == max)
                .findFirst()
                .get()
                .getKey();

        System.out.println(String.format("The best location for the monitoring station is at astroid %s with %d astroids in its direct line of sight", astroid, max));

        final LineOfSightMap monitoringAstroid = lineOfSightMaps.get(astroid);
        final Set<LineOfSight> linesOfSight = monitoringAstroid.map();

        Iterator<LineOfSight> iter = linesOfSight.iterator();
        Optional<Astroid> result = Optional.empty();
        int count = 0;
        while (count < 200) {
            if (!iter.hasNext())
                iter = linesOfSight.iterator();

            result = iter.next().vaporize();
            if (result.isPresent())
                count++;
        }

        System.out.println("200th atroid vaporized was " + result.get());
    }
}
