package com.putoet.day10;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class LineOfSightTest {
    private Astroid origin = new Astroid(new Point(3, 4));
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
    public void testAdd() {
        final LineOfSight los = LineOfSight.of(origin, astroids[0]);
        Arrays.stream(astroids).forEach(los::add);

        assertEquals(origin, los.origin());
        assertEquals(new Vector(-1, -2), los.direction());
        assertEquals(astroids[4], los.inLineOfSight());
        assertEquals(Set.of(astroids[0]), los.hidden());
    }
}