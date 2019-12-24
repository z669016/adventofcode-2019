package com.putoet.Day8;

import org.junit.Test;

import static org.junit.Assert.*;

public class DimensionTest {
    @Test
    public void testSize() {
        final Dimension dimension = Dimension.of(3, 7);
        assertEquals(21, dimension.size());
    }
}