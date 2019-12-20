package com.putoet.day5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MemoryTest {
    @Test
    public void testCreateMemory() {
        final List<Integer> integerList = List.of(1,9,10,3,2,3,11,0,99,30,40,50);
        final Memory memory = Memory.of(integerList);
        assertEquals(memory.size(), integerList.size());
        assertEquals("1,9,10,3,2,3,11,0,99,30,40,50", memory.dump());

        try {
            Memory.of(null);
        } catch (IllegalArgumentException exc) {
            assertEquals("No memory", exc.getMessage());
        }

        try {
            Memory.of(Collections.emptyList());
        } catch (IllegalArgumentException exc) {
            assertEquals("No memory", exc.getMessage());
        }
    }

    @Test
    public void testAccessMemory() {
        final Memory memory = Memory.of(List.of(1,9,10,3,2,3,11,0,99,30,40,50));

        assertEquals(memory.peek(Address.of(0)), Integer.valueOf(1));
        assertEquals(memory.peek(Address.of(11)), Integer.valueOf(50));

        memory.poke(Address.of(9), 31);
        assertEquals(memory.peek(Address.of(9)), Integer.valueOf(31));

        try {
            memory.peek(Address.of(12));
        } catch (IllegalArgumentException exc) {
            assertEquals("Invalid memory address 12", exc.getMessage());
        }

        try {
            Memory.of(null);
        } catch (IllegalArgumentException exc) {
            assertEquals("No memory", exc.getMessage());
        }

        try {
            Memory.of(new ArrayList<Integer>());
        } catch (IllegalArgumentException exc) {
            assertEquals("No memory", exc.getMessage());
        }
    }

}
