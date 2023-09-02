package com.putoet.day10;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LineOfSightTest {
    @Test
    public void testAdd() {
        final var origin = new Asteroid(Point.of(3, 4));
        final var asteroids = new Asteroid[]{
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
        final var los = LineOfSight.of(origin, asteroids[0]);
        Arrays.stream(asteroids).forEach(los::add);

        assertEquals(origin, los.origin());
        assertEquals(new Vector(-1, -2), los.direction());
        assertEquals(asteroids[4], los.inLineOfSight().orElseThrow());
        assertEquals(Set.of(asteroids[0]), los.hidden());
    }

    @Test
    public void testHiddenOrder() {
        final var origin = new Asteroid(Point.of(0, 2));
        final var asteroids = new Asteroid[]{
                new Asteroid(Point.of(3, 2)),
                new Asteroid(Point.of(0, 2)),
                new Asteroid(Point.of(4, 2)),
                new Asteroid(Point.of(2, 2)),
                new Asteroid(Point.of(1, 2))
        };
        final var los = LineOfSight.of(origin, asteroids[0]);
        Arrays.stream(asteroids).forEach(los::add);

        assertEquals(List.of(asteroids[3], asteroids[0], asteroids[2]), new ArrayList<>(los.hidden()));
    }

    @Test
    public void testVaporize() {
        final var origin = new Asteroid(Point.of(0, 2));
        final var asteroids = new Asteroid[]{
                new Asteroid(Point.of(3, 2)),
                new Asteroid(Point.of(0, 2)),
                new Asteroid(Point.of(4, 2)),
                new Asteroid(Point.of(2, 2)),
                new Asteroid(Point.of(1, 2))
        };
        final var los = LineOfSight.of(origin, asteroids[0]);
        Arrays.stream(asteroids).forEach(los::add);

        assertTrue(los.vaporize().isPresent());
        assertEquals(asteroids[3], los.inLineOfSight().get());
        assertTrue(los.vaporize().isPresent());
        assertEquals(asteroids[0], los.inLineOfSight().get());
        assertTrue(los.vaporize().isPresent());
        assertEquals(asteroids[2], los.inLineOfSight().get());
        assertTrue(los.vaporize().isPresent());
        assertTrue(los.inLineOfSight().isEmpty());
        assertTrue(los.vaporize().isEmpty());
    }
}