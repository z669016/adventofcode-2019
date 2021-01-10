package com.putoet.day8;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SizeTest {
    @Test
    public void testSize() {
        final Size size = Size.of(3, 7);
        assertEquals(21, size.size());
    }
}