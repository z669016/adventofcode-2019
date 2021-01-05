package com.putoet.intcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedMemoryTest {
    private Memory memory;

    @BeforeEach
    void setup() {
        memory = new FixedMemory(List.of(1, 2));
    }


    @Test
    void peekExpand() {
        assertThrows(Memory.InvalidMemoryAddress.class, () -> memory.peek(Address.START.increase(2)));
    }
}