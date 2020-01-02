package com.putoet.day9;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RelativeBaseMemoryAccessorTest {
    private static final List<Integer> memoryList = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    private Memory memory;
    private RelativeBaseMemoryAccessor accessor;

    @Before
    public void setup() {
        memory = Memory.ofIntegerList(memoryList);
        accessor = new RelativeBaseMemoryAccessor(Address.of(3), Address.of(1), memory);
    }

    @Test
    public void peek() {
        assertEquals(Long.valueOf(4), accessor.peek());
    }

    @Test
    public void poke() {
        accessor.poke(10L);
        assertEquals(List.of(0L, 1L, 2L, 3L, 10L, 5L, 6L, 7L, 8L, 9L), memory.asList());
    }

    @Test
    public void testToString() {
        assertEquals(" 4 (<-- 4 <-- (3 + 1))", accessor.toString());
    }
}