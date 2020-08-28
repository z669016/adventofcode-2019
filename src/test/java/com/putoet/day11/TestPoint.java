package com.putoet.day11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPoint {
    @Test
    public void testMoveLeft() {
        assertEquals(new Point(0, 1), new Point(1, 1).moveLeft());
    }

    @Test
    public void testMoveUp() {
        assertEquals(new Point(0, 1), new Point(0, 0).moveUp());
    }

    @Test
    public void testMoveRight() {
        assertEquals(new Point(0, 1), new Point(-1, 1).moveRight());
    }

    @Test
    public void testMoveDown() {
        assertEquals(new Point(0, 1), new Point(0, 2).moveDown());
    }
}
