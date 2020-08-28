package com.putoet.day3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @Test
    public void testDirection() {
        assertEquals(Direction.UP, Direction.of('U'));
        assertEquals(Direction.DOWN, Direction.of('D'));
        assertEquals(Direction.LEFT, Direction.of('L'));
        assertEquals(Direction.RIGHT, Direction.of('R'));

        try {
            Direction.of(null);
        } catch (IllegalArgumentException exc) {
            assertEquals("Not a direction", exc.getMessage());
        }

        try {
            Direction.of('q');
        } catch (IllegalArgumentException exc) {
            assertEquals("Not a direction: q", exc.getMessage());
        }
    }
}
