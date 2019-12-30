package com.putoet.day9;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LessThanInstructionTest {
    private LessThanInstruction instruction;
    private Registers regs;
    private IMemoryAccessor accessorA;
    private IMemoryAccessor accessorB;
    private IMemoryAccessor accessorC;

    @Before
    public void setup() {
        regs = new Registers();

        accessorA = mock(ImmediateMemoryAccessor.class);
        accessorB = mock(ImmediateMemoryAccessor.class);
        when(accessorB.peek()).thenReturn(13L);

        accessorC = mock(PositionMemoryAccessor.class);

        instruction = new LessThanInstruction(regs, accessorA, accessorB, accessorC);
    }

    @Test
    public void testConstructor() {
        assertEquals(Operation.LESS_THAN, instruction.operation());
    }

    @Test
    public void testExecuteTrue() {
        when(accessorA.peek()).thenReturn(7L);

        final Optional<Registers> updated = instruction.execute();
        assertTrue(updated.isPresent());
        assertEquals(Address.of(Operation.SUM.size()), updated.get().ip());
        assertEquals(Address.START_ADDRESS, updated.get().rb());
        verify(accessorC).poke(eq(Long.valueOf(1L)));
    }

    @Test
    public void testExecuteFalse() {
        when(accessorA.peek()).thenReturn(17L);

        final Optional<Registers> updated = instruction.execute();
        assertTrue(updated.isPresent());
        assertEquals(Address.of(Operation.SUM.size()), updated.get().ip());
        assertEquals(Address.START_ADDRESS, updated.get().rb());
        verify(accessorC).poke(eq(Long.valueOf(0L)));
    }
}
