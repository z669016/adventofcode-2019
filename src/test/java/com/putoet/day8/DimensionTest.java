package com.putoet.day8;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DimensionTest {
    @Test
    public void testSize() {
        final Dimension dimension = Dimension.of(3, 7);
        assertEquals(21, dimension.size());
    }
}