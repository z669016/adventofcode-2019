package com.putoet.day10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTest {

    @Test
    public void distanceTo() {
        assertEquals(5.0, new Point(0, 0).distanceTo(new Point(3, 4)));
        assertEquals(5.0, new Point(3, 8).distanceTo(new Point(6, 12)));
    }
}