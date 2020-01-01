package com.putoet.day10;


import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class VectorTest {
    @Test
    public void testOfPoints() {
        assertEquals(new Vector(3, 8), Vector.ofPoints(new Point(0, 0), new Point(3, 8)));
        assertEquals(new Vector(3, 8), Vector.ofPoints(new Point(4, 7), new Point(7, 15)));
        assertEquals(new Vector(-1, 1), Vector.ofPoints(new Point(4, 7), new Point(3, 8)));
        assertEquals(new Vector(1, -1), Vector.ofPoints(new Point(4, 7), new Point(5, 6)));
    }

    @Test
    public void testSimplified() {
        assertEquals(new Vector(1, 1), new Vector(8, 8).direction());
        assertEquals(new Vector(3, 4), new Vector(36, 48).direction());
        assertEquals(new Vector(1, 3), new Vector(14, 42).direction());
    }

    @Test
    public void testLength() {
        assertEquals(5.0, new Vector(3, 4).length());
    }
}
