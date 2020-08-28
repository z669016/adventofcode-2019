package com.putoet.day9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdjustRelativeBaseInstructionTest {
    private AdjustRelativeBaseInstruction instruction;
    private Registers regs;
    private IMemoryAccessor accessorA;

    @BeforeEach
    public void setup() {
        regs = new Registers();
        accessorA = mock(ImmediateMemoryAccessor.class);
        when(accessorA.peek()).thenReturn(7L);

        instruction = new AdjustRelativeBaseInstruction(regs, accessorA);
    }

    @Test
    public void testConstructor() {
        assertEquals(Operation.ADJUST_RELATIVE_BASE, instruction.operation());
    }

    @Test
    public void testExecute() {
        final Optional<Registers> updated = instruction.execute();
        assertTrue(updated.isPresent());
        assertEquals(regs.ip().increase(Operation.ADJUST_RELATIVE_BASE.size()), updated.get().ip());
        assertEquals(Address.of(7), updated.get().rb());
    }
}
