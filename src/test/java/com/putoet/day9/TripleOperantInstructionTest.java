package com.putoet.day9;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TripleOperantInstructionTest {
    private TripleOperantInstruction instruction;
    private Registers regs;
    private IMemoryAccessor accessorA;
    private IMemoryAccessor accessorB;
    private IMemoryAccessor accessorC;

    @Before
    public void setup() {
        regs = new Registers();

        accessorA = mock(ImmediateMemoryAccessor.class);
        when(accessorA.peek()).thenReturn(7L);
        when(accessorA.toString()).thenReturn(" 7 (<-- 10)");

        accessorB = mock(ImmediateMemoryAccessor.class);
        when(accessorB.peek()).thenReturn(2L);
        when(accessorB.toString()).thenReturn(" 2 (<-- 11)");

        accessorC = mock(ImmediateMemoryAccessor.class);
        when(accessorC.peek()).thenReturn(3L);
        when(accessorC.toString()).thenReturn(" 3 (<-- 13)");

        instruction = new TripleOperantInstruction(regs, Operation.SUM, accessorA, accessorB, accessorC) {};
    }

    @Test
    public void testGetC() {
        assertEquals(Long.valueOf(7), instruction.a());
        assertEquals(Long.valueOf(2), instruction.b());
        assertEquals(Long.valueOf(3), instruction.c());
    }

    @Test
    public void testSetC() {
        instruction.a(5L);
        instruction.b(9L);
        instruction.c(17L);

        verify(accessorA).poke(eq(Long.valueOf(5)));
        verify(accessorB).poke(eq(Long.valueOf(9)));
        verify(accessorC).poke(eq(Long.valueOf(17)));
    }

    @Test
    public void testToString() {
        assertEquals("SUM 7 (<-- 10) 2 (<-- 11) 3 (<-- 13)", instruction.toString());
    }
}
