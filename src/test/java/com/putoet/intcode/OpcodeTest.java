package com.putoet.intcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpcodeTest {
    @Test
    void opcode() {
        assertEquals(3, new Opcode(3).opcode());
        assertEquals(99, new Opcode(99).opcode());
        assertEquals(3, new Opcode(103).opcode());
        assertEquals(3, new Opcode(1103).opcode());
        assertEquals(3, new Opcode(11103).opcode());
    }

    @Test
    void mode() {
        assertEquals(Mode.POSITION, new Opcode(3).mode1());
        assertEquals(Mode.POSITION, new Opcode(3).mode2());
        assertEquals(Mode.POSITION, new Opcode(3).mode3());

        assertEquals(Mode.IMMEDIATE, new Opcode(103).mode1());
        assertEquals(Mode.POSITION, new Opcode(103).mode2());
        assertEquals(Mode.POSITION, new Opcode(103).mode3());

        assertEquals(Mode.POSITION, new Opcode(1003).mode1());
        assertEquals(Mode.IMMEDIATE, new Opcode(1003).mode2());
        assertEquals(Mode.POSITION, new Opcode(1003).mode3());

        assertEquals(Mode.POSITION, new Opcode(10003).mode1());
        assertEquals(Mode.POSITION, new Opcode(10003).mode2());
        assertEquals(Mode.IMMEDIATE, new Opcode(10003).mode3());
    }
}