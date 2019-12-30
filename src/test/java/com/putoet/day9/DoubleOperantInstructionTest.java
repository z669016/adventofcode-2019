package com.putoet.day9;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DoubleOperantInstructionTest {
    private DoubleOperantInstruction instruction;
    private Registers regs;
    private IMemoryAccessor accessorA;
    private IMemoryAccessor accessorB;

    @Before
    public void setup() {
        regs = mock(Registers.class);

        accessorA = mock(ImmediateMemoryAccessor.class);
        when(accessorA.peek()).thenReturn(7L);
        when(accessorA.toString()).thenReturn(" 7 (<-- 10)");

        accessorB = mock(ImmediateMemoryAccessor.class);
        when(accessorB.peek()).thenReturn(2L);
        when(accessorB.toString()).thenReturn(" 2 (<-- 11)");

        instruction = new DoubleOperantInstruction(regs, Operation.JUMP_IF_TRUE, accessorA, accessorB) {};
    }

    @Test
    public void testGetB() {
        assertEquals(Long.valueOf(7), instruction.a());
        assertEquals(Long.valueOf(2), instruction.b());
    }

    @Test
    public void testSetB() {
        instruction.a(5L);
        instruction.b(9L);

        verify(accessorA).poke(eq(Long.valueOf(5)));
        verify(accessorB).poke(eq(Long.valueOf(9)));
    }

    @Test
    public void testToString() {
        assertEquals("JUMP_IF_TRUE 7 (<-- 10) 2 (<-- 11)", instruction.toString());
    }
}