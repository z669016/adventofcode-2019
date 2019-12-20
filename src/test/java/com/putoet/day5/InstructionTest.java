package com.putoet.day5;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class InstructionTest {
    @Test
    public void testMode() {
        assertEquals(InstructionMode.POSITION_MODE, InstructionMode.of(0));
        assertEquals(InstructionMode.IMMEDIATE_MODE, InstructionMode.of(1));

        IllegalArgumentException ia = null;
        try {
            InstructionMode.of(2);
            fail("Mode of 2 should not be accepted");
        } catch (IllegalArgumentException exc) {
            ia = exc;
        }
        assertNotNull(ia);
        assertEquals("Invalid mode '2'", ia.getMessage());
    }

    @Test
    public void testOperantModes() {
        Instruction instruction = InstructionFactory.of(1);
        assertEquals(InstructionMode.POSITION_MODE, instruction.modes[0]);
        assertEquals(InstructionMode.POSITION_MODE, instruction.modes[1]);

        instruction = InstructionFactory.of(101);
        assertEquals(Operation.SUM, instruction.operation());
        assertEquals(InstructionMode.IMMEDIATE_MODE, instruction.modes[0]);
        assertEquals(InstructionMode.POSITION_MODE, instruction.modes[1]);

        instruction = InstructionFactory.of(1001);
        assertEquals(Operation.SUM, instruction.operation());
        assertEquals(InstructionMode.POSITION_MODE, instruction.modes[0]);
        assertEquals(InstructionMode.IMMEDIATE_MODE, instruction.modes[1]);
    }
}
