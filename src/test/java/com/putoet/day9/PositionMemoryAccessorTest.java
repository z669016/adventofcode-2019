package com.putoet.day9;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PositionMemoryAccessorTest {
    private static final List<Integer> memoryList = List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

    private Memory memory;
    private PositionMemoryAccessor accessor;

    @Before
    public void setup() {
        memory = Memory.ofIntegerList(memoryList);
        accessor = new PositionMemoryAccessor(Address.of(3), memory);
    }

    @Test
    public void peek() {
        assertEquals(Long.valueOf(3), accessor.peek());
    }

    @Test
    public void poke() {
        accessor.poke(10L);
        assertEquals(List.of(9L, 8L, 7L, 6L, 5L, 4L, 10L, 2L, 1L, 0L), memory.asList());
    }

    @Test
    public void testToString() {
        assertEquals(" 3 (<-- 6 <-- 3)", accessor.toString());
    }
}