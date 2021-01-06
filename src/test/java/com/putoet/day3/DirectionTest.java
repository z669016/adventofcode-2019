package com.putoet.day3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DirectionTest {

    @Test
    public void testDirection() {
        assertEquals(Direction.UP, Direction.of('U'));
        assertEquals(Direction.DOWN, Direction.of('D'));
        assertEquals(Direction.LEFT, Direction.of('L'));
        assertEquals(Direction.RIGHT, Direction.of('R'));

        assertThrows(AssertionError.class, () -> Direction.of(null));
        assertThrows(AssertionError.class, () -> Direction.of('q'));
    }
}
