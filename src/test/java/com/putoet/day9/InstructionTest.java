package com.putoet.day9;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class InstructionTest {
    private Instruction instruction;

    @Before
    public void setup() {
        instruction = new Instruction(new Registers(), Operation.EXIT) {
        };
    }

    @Test
    public void testOperation() {
        assertEquals(Operation.EXIT, instruction.operation());
    }

    @Test
    public void testRun() {
        final Registers updated = new Registers(Address.of(Operation.EXIT.size()), Address.START_ADDRESS);
        final Optional<Registers> regs = instruction.execute();
        assertTrue(regs.isPresent());
        assertEquals(updated.rb(), regs.get().rb());
        assertEquals(updated.ip(), regs.get().ip());
    }
}