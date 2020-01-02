package com.putoet.day10;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;

public class LineOfSightTest {
    @Test
    public void testAdd() {
        final Astroid origin = new Astroid(new Point(3, 4));
        final Astroid[] astroids = new Astroid[]{
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
        final LineOfSight los = LineOfSight.of(origin, astroids[0]);
        Arrays.stream(astroids).forEach(los::add);

        assertEquals(origin, los.origin());
        assertEquals(new Vector(-1, -2), los.direction());
        assertEquals(astroids[4], los.inLineOfSight().get());
        assertEquals(Set.of(astroids[0]), los.hidden());
    }

    @Test
    public void testHiddenOrder() {
        final Astroid origin = new Astroid(new Point(0, 2));
        final Astroid[] astroids = new Astroid[]{
                new Astroid(new Point(3, 2)),
                new Astroid(new Point(0, 2)),
                new Astroid(new Point(4, 2)),
                new Astroid(new Point(2, 2)),
                new Astroid(new Point(1, 2))
        };
        final LineOfSight los = LineOfSight.of(origin, astroids[0]);
        Arrays.stream(astroids).forEach(los::add);

        assertEquals(List.of(astroids[3], astroids[0], astroids[2]), los.hidden().stream().collect(Collectors.toList()));
    }

    @Test
    public void testVaporize() {
        final Astroid origin = new Astroid(new Point(0, 2));
        final Astroid[] astroids = new Astroid[]{
                new Astroid(new Point(3, 2)),
                new Astroid(new Point(0, 2)),
                new Astroid(new Point(4, 2)),
                new Astroid(new Point(2, 2)),
                new Astroid(new Point(1, 2))
        };
        final LineOfSight los = LineOfSight.of(origin, astroids[0]);
        Arrays.stream(astroids).forEach(los::add);

        // System.out.println(los.hidden());

        assertTrue(los.vaporize().isPresent());
        assertEquals(astroids[3], los.inLineOfSight().get());
        assertTrue(los.vaporize().isPresent());
        assertEquals(astroids[0], los.inLineOfSight().get());
        assertTrue(los.vaporize().isPresent());
        assertEquals(astroids[2], los.inLineOfSight().get());
        assertTrue(los.vaporize().isPresent());
        assertTrue(los.inLineOfSight().isEmpty());
        assertTrue(los.vaporize().isEmpty());
    }
}