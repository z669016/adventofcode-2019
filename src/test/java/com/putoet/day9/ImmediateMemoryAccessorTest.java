package com.putoet.day9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImmediateMemoryAccessorTest {
    private static final List<Integer> memoryList = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    private Memory memory;
    private ImmediateMemoryAccessor accessor;

    @BeforeEach
    public void setup() {
        memory = Memory.ofIntegerList(memoryList);
        accessor = new ImmediateMemoryAccessor(Address.of(4), memory);
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
        assertEquals(" 4 (<-- 4)", accessor.toString());
    }
}