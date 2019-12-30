package com.putoet.day9;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class JumpIfTrueInstructionTest {
    private JumpIfTrueInstruction instruction;
    private Registers regs;
    private IMemoryAccessor accessorA;
    private IMemoryAccessor accessorB;

    @Before
    public void setup() {
        regs = new Registers();
        accessorA = mock(ImmediateMemoryAccessor.class);
        accessorB = mock(ImmediateMemoryAccessor.class);
        when(accessorB.peek()).thenReturn(7L);

        instruction = new JumpIfTrueInstruction(regs, accessorA, accessorB);
    }

    @Test
    public void testConstructor() {
        assertEquals(Operation.JUMP_IF_TRUE, instruction.operation());
    }

    @Test
    public void testExecuteTrue() {
        when(accessorA.peek()).thenReturn(1L);
        final Optional<Registers> updated = instruction.execute();
        assertTrue(updated.isPresent());
        assertEquals(Address.of(7), updated.get().ip());
        assertEquals(Address.START_ADDRESS, updated.get().rb());
    }

    @Test
    public void testExecuteFalse() {
        when(accessorA.peek()).thenReturn(0L);
        final Optional<Registers> updated = instruction.execute();
        assertTrue(updated.isPresent());
        assertEquals(regs.withIncreasedInstructionPointer(Operation.JUMP_IF_TRUE.size()).ip(), updated.get().ip());
        assertEquals(Address.START_ADDRESS, updated.get().rb());
    }
}
