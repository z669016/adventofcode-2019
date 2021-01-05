package com.putoet.day9;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemoryTest {
    @Test
    public void testCreateMemory() {
        final List<Integer> integerList = List.of(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50);
        final Memory memory = Memory.ofIntegerList(integerList);

        assertEquals(memory.size(), integerList.size());
        assertEquals(List.of(1L, 9L, 10L, 3L, 2L, 3L, 11L, 0L, 99L, 30L, 40L, 50L), memory.asList());

        assertThrows(IllegalArgumentException.class, () -> Memory.ofIntegerList(null));
        assertThrows(IllegalArgumentException.class, () -> Memory.ofIntegerList(Collections.emptyList()));
    }

    @Test
    public void testAccessMemory() {
        final List<Integer> longList = List.of(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50);
        final Memory memory = Memory.ofIntegerList(longList);

        assertEquals(memory.peek(Address.of(0)), Long.valueOf(1));
        assertEquals(memory.peek(Address.of(11)), Long.valueOf(50));

        memory.poke(Address.of(9), 31L);
        assertEquals(memory.peek(Address.of(9)), Long.valueOf(31));

        assertEquals(Long.valueOf(0), memory.peek(Address.of(12)));
    }
}
