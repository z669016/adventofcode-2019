package com.putoet.intcode;

import com.putoet.intcode.Address;
import com.putoet.intcode.ExpandableMemory;
import com.putoet.intcode.Memory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpandableMemoryTest {
    private Memory memory;

    @BeforeEach
    void setup() {
        memory = new ExpandableMemory(List.of(1L, 2L));
    }

    @Test
    void peek() {
        assertEquals(1, memory.peek(Address.START));
        assertEquals(2, memory.peek(Address.START.increase(1)));
        assertEquals(2, memory.size());
    }

    @Test
    void peekExpand() {
        assertEquals(0, memory.peek(Address.START.increase(2)));
        assertEquals(14, memory.size());
    }

    @Test
    void poke() {
        memory.poke(Address.START.increase(1), 7);
        assertEquals(7, memory.peek(Address.START.increase(1)));
    }

    @Test
    void asList() {
        assertArrayEquals(new long[]{1, 2}, memory.toArray());
    }
}