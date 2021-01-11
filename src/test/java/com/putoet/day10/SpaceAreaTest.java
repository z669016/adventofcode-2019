package com.putoet.day10;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpaceAreaTest {
    final SpaceArea spaceArea = SpaceArea.of(List.of(
            ".#..#",
            ".....",
            "#####",
            "....#",
            "...##"));

    private final Asteroid[] asteroids = new Asteroid[]{
            new Asteroid(Point.of(1, 0)),
            new Asteroid(Point.of(4, 0)),
            new Asteroid(Point.of(1, 2)),
            new Asteroid(Point.of(0, 2)),
            new Asteroid(Point.of(2, 2)),
            new Asteroid(Point.of(3, 2)),
            new Asteroid(Point.of(4, 2)),
            new Asteroid(Point.of(4, 3)),
            new Asteroid(Point.of(3, 4)),
            new Asteroid(Point.of(4, 4))
    };

    @Test
    public void testAsteroidMapOf() {
        assertTrue(spaceArea.asteroids().containsAll(Arrays.asList(asteroids)));
    }

    @Test
    public void testLeastVisible() {
        final Map<Asteroid, LineOfSightMap> lineOfSightMaps = spaceArea.linesOfSightMaps();
        assertEquals(5, lineOfSightMaps.values().stream().mapToInt(LineOfSightMap::inLineOfSightCount).min().getAsInt());
    }

    @Test
    public void testMaxVisible() {
        final Map<Asteroid, LineOfSightMap> lineOfSightMaps = spaceArea.linesOfSightMaps();
        assertEquals(8, lineOfSightMaps.values().stream().mapToInt(LineOfSightMap::inLineOfSightCount).max().getAsInt());
    }
}
