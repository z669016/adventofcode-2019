package com.putoet.day9;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SumInstructionTest {
    private SumInstruction instruction;
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

        instruction = new SumInstruction(regs, accessorA, accessorB, accessorC);
    }

    @Test
    public void testConstructor() {
        assertEquals(Operation.SUM, instruction.operation());
    }

    @Test
    public void testExecute() {
        instruction.execute();

        verify(accessorC).poke(eq(Long.valueOf(9L)));
    }
}
