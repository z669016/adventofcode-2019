package com.putoet.day12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {

    @Test
    public void testOf() {
        final Position p1 = Position.of("<x=-7, y=-8, z=9>");
        assertEquals(new Position(-7, -8, 9), p1);
    }
}