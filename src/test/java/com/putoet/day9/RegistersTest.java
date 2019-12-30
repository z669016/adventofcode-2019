package com.putoet.day9;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegistersTest {
    @Test
    public void testNew() {
        Registers regs = new Registers();

        assertEquals(Address.START_ADDRESS, regs.ip());
        assertEquals(Address.START_ADDRESS, regs.rb());
    }

    @Test
    public void testWithIncreasedInstructionPointer() {
        Registers regs = new Registers().withIncreasedInstructionPointer(7);

        assertEquals(Address.START_ADDRESS.increase(7), regs.ip());
        assertEquals(Address.START_ADDRESS, regs.rb());
    }

    @Test
    public void testWithIncreasedRelativeBase() {
        Registers regs = new Registers().withIncreasedRelativeBase(7);

        assertEquals(Address.START_ADDRESS, regs.ip());
        assertEquals(Address.START_ADDRESS.increase(7), regs.rb());
    }
}
