package com.putoet.intcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModeTest {

    @Test
    void testToString() {
        assertEquals("0", Mode.POSITION.toString());
        assertEquals("1", Mode.IMMEDIATE.toString());
        assertEquals("2", Mode.RELATIVE.toString());
    }
}