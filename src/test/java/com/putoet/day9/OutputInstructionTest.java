package com.putoet.day9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OutputInstructionTest {
    private Registers regs;
    private IMemoryAccessor accessor;
    private OutputDevice outputDevice;
    private OutputInstruction instruction;

    @BeforeEach
    public void setup() {
        regs = new Registers();

        accessor = mock(IMemoryAccessor.class);
        when(accessor.peek()).thenReturn(13L);
        outputDevice = new OutputDevice();

        instruction = new OutputInstruction(regs, accessor, outputDevice);
    }

    @Test
    public void testConstructor() {
        assertEquals(Operation.OUTPUT, instruction.operation());
    }

    @Test
    public void execute() {
        final Optional<Registers> regs = instruction.execute();

        assertTrue(regs.isPresent());
        assertEquals(Address.START_ADDRESS, regs.get().rb());
        assertEquals(Address.of(Operation.OUTPUT.size()), regs.get().ip());
        assertEquals(List.of(13L), outputDevice.asList());
    }
}