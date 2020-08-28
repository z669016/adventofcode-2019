package com.putoet.day10;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AstroidMapTest {
    final AstroidMap astroidMap = AstroidMap.of(List.of(
            ".#..#",
            ".....",
            "#####",
            "....#",
            "...##"));

    private Astroid[] astroids = new Astroid[]{
            new Astroid(new Point(1, 0)),
            new Astroid(new Point(4, 0)),
            new Astroid(new Point(1, 2)),
            new Astroid(new Point(0, 2)),
            new Astroid(new Point(2, 2)),
            new Astroid(new Point(3, 2)),
            new Astroid(new Point(4, 2)),
            new Astroid(new Point(4, 3)),
            new Astroid(new Point(3, 4)),
            new Astroid(new Point(4, 4))
    };

    @Test
    public void testAstroidMapOf() {
        assertTrue(astroidMap.astroids().containsAll(Arrays.asList(astroids)));
//
//        final Map<Astroid, LineOfSightMap> lineOfSightMaps = astroidMap.linesOfSightMaps();
//        lineOfSightMaps.forEach((k, v) -> System.out.println(v));
    }

    @Test
    public void testLeastVisible() {
        final Map<Astroid, LineOfSightMap> lineOfSightMaps = astroidMap.linesOfSightMaps();
        assertEquals(5, lineOfSightMaps.values().stream().mapToInt(LineOfSightMap::inLineOfSightCount).min().getAsInt());
    }

    @Test
    public void testMaxVisible() {
        final Map<Astroid, LineOfSightMap> lineOfSightMaps = astroidMap.linesOfSightMaps();
        assertEquals(8, lineOfSightMaps.values().stream().mapToInt(LineOfSightMap::inLineOfSightCount).max().getAsInt());
    }
}
