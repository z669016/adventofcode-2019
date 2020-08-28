package com.putoet.day2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemoryTest {

    @Test
    public void testCreateMemory() {
        final List<Integer> integerList = List.of(1,9,10,3,2,3,11,0,99,30,40,50);
        final Memory memory = Memory.of(integerList);

        assertEquals(memory.size(), integerList.size());
        assertEquals("1,9,10,3,2,3,11,0,99,30,40,50", memory.dump());

        assertThrows(AssertionError.class, () -> Memory.of(null));
        assertThrows(AssertionError.class, () -> Memory.of(List.of()));
    }

    @Test
    public void testAccessMemory() {
        final Memory memory = Memory.of(List.of(1,9,10,3,2,3,11,0,99,30,40,50));

        assertEquals(memory.peek(Address.of(0)), Integer.valueOf(1));
        assertEquals(memory.peek(Address.of(11)), Integer.valueOf(50));

        memory.poke(Address.of(9), 31);
        assertEquals(memory.peek(Address.of(9)), Integer.valueOf(31));

        assertThrows(IllegalArgumentException.class, () -> memory.peek(Address.of(12)));
    }

}
