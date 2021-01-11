package com.putoet.day10;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineOfSightMapTest {
    private final Asteroid origin = new Asteroid(Point.of(4, 2));
    private final Asteroid[] asteroids = new Asteroid[]{
            new Asteroid(Point.of(1, 0)),
            new Asteroid(Point.of(4, 0)),
            new Asteroid(Point.of(1, 2)),
            new Asteroid(Point.of(0, 2)),
            new Asteroid(Point.of(2, 2)),
            new Asteroid(Point.of(3, 2)),
            new Asteroid(Point.of(4, 3)),
            new Asteroid(Point.of(3, 4)),
            new Asteroid(Point.of(4, 4))
    };

    @Test
    public void testAdd() {
        final LineOfSightMap lineOfSightMap = new LineOfSightMap(origin);
        Arrays.stream(asteroids).forEach(lineOfSightMap::add);

        System.out.println("In line of sight: " + lineOfSightMap.inLineOfSight());
        System.out.println("Hidden: " + lineOfSightMap.hidden());
        assertEquals(5, lineOfSightMap.inLineOfSightCount());

        System.out.println(lineOfSightMap.map());
    }
}