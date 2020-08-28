package com.putoet.day9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class InputInstructionTest {
    private Registers regs;
    private IMemoryAccessor accessor;
    private IInputDevice inputDevice;
    private InputInstruction instruction;

    @BeforeEach
    public void setup() {
        regs = new Registers();
        accessor = mock(IMemoryAccessor.class);
        inputDevice = new ResumableInputDevice(List.of());

        instruction = new InputInstruction(regs, accessor, inputDevice);
    }

    @Test
    public void testConstructor() {
        assertEquals(Operation.INPUT, instruction.operation());
    }

    @Test
    public void executeEmpty() {
        final Optional<Registers> regs = instruction.execute();
        assertTrue(regs.isEmpty());
    }

    @Test
    public void execute() {
        inputDevice.put(3L);
        final Optional<Registers> regs = instruction.execute();
        verify(accessor).poke(eq(3L));
        assertTrue(regs.isPresent());
        assertEquals(Address.START_ADDRESS, regs.get().rb());
        assertEquals(Address.of(Operation.INPUT.size()), regs.get().ip());
    }
}