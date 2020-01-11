package com.putoet.day15;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestPoint {

    @Test
    public void testOf() {
        assertEquals(new Point(3, 7), Point.of(3, 7));
    }

    @Test
    public void testRelativeTo() {
        assertEquals(new Point(3, 7), Point.of(1, 2).relativeTo(Point.of(2, 5)));
    }

    @Test
    public void testMove() {
        assertEquals(Point.of(0, 1), Point.ORIGIN.move(Direction.NORTH));
        assertEquals(Point.of(0, -1), Point.ORIGIN.move(Direction.SOUTH));
        assertEquals(Point.of(1, 0), Point.ORIGIN.move(Direction.WEST));
        assertEquals(Point.of(-1, 0), Point.ORIGIN.move(Direction.EAST));
    }

    @Test
    public void testMoveOpposite() {
        assertEquals(Point.of(0, 1), Point.ORIGIN.moveOpposite(Direction.SOUTH));
        assertEquals(Point.of(0, -1), Point.ORIGIN.moveOpposite(Direction.NORTH));
        assertEquals(Point.of(1, 0), Point.ORIGIN.moveOpposite(Direction.EAST));
        assertEquals(Point.of(-1, 0), Point.ORIGIN.moveOpposite(Direction.WEST));
    }
}