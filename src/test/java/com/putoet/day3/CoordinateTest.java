package com.putoet.day3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinateTest {
    @Test
    public void testCoordinate() {
        assertEquals(Coordinate.of(0, 1), Coordinate.ORIGIN.move(Direction.UP));
        assertEquals(Coordinate.of(0, -1), Coordinate.ORIGIN.move(Direction.DOWN));
        assertEquals(Coordinate.of(-1, 0), Coordinate.ORIGIN.move(Direction.LEFT));
        assertEquals(Coordinate.of(1, 0), Coordinate.ORIGIN.move(Direction.RIGHT));
    }

    @Test
    public void testCoordinateManhattanDistance() {
        Coordinate c1 = Coordinate.of(0, 0);
        assertEquals(0, c1.manhattanDistance());

        c1 = Coordinate.of(-1, -3);
        assertEquals(4, c1.manhattanDistance());

        c1 = Coordinate.of(3, 7);
        assertEquals(10, c1.manhattanDistance());
    }
}
