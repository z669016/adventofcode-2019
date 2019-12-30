package com.putoet.day9;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ProductInstructionTest {
    private ProductInstruction instruction;
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

        accessorC = mock(PositionMemoryAccessor.class);

        instruction = new ProductInstruction(regs, accessorA, accessorB, accessorC);
    }

    @Test
    public void testConstructor() {
        assertEquals(Operation.PRODUCT, instruction.operation());
    }

    @Test
    public void testExecute() {
        final Optional<Registers> updated = instruction.execute();
        assertTrue(updated.isPresent());
        assertEquals(Address.of(Operation.SUM.size()), updated.get().ip());
        assertEquals(Address.START_ADDRESS, updated.get().rb());
        verify(accessorC).poke(eq(Long.valueOf(14L)));
    }
}
