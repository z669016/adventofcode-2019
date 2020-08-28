package com.putoet.day10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testDegrees() {
//        System.out.println(new Vector(0, -3).degrees());
//        System.out.println(new Vector(3, -3).degrees());
//        System.out.println(new Vector(3, 0).degrees());
//        System.out.println(new Vector(3, 3).degrees());
//        System.out.println(new Vector(0, 3).degrees());
//        System.out.println(new Vector(-3, 3).degrees());
//        System.out.println(new Vector(-3, 0).degrees());
//        System.out.println(new Vector(-3, -3).degrees());
//
        assertEquals(0.0, new Vector(0, -3).degrees());
        assertEquals(45.0, new Vector(3, -3).degrees());
        assertEquals(90.0, new Vector(3, 0).degrees());
        assertEquals(135.0, new Vector(3, 3).degrees());
        assertEquals(180.0, new Vector(0, 3).degrees());
        assertEquals(225.0, new Vector(-3, 3).degrees());
        assertEquals(270.0, new Vector(-3, 0).degrees());
        assertEquals(315.0, new Vector(-3, -3).degrees());
    }
}
