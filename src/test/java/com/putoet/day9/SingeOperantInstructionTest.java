package com.putoet.day9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SingeOperantInstructionTest {
    private SingeOperantInstruction instruction;
    private Registers regs;
    private IMemoryAccessor accessor;

    @BeforeEach
    public void setup() {
        regs  = mock(Registers.class);

        accessor = mock(ImmediateMemoryAccessor.class);
        when(accessor.peek()).thenReturn(7L);
        when(accessor.toString()).thenReturn(" 7 (<-- 10)");

        instruction = new SingeOperantInstruction(regs, Operation.INPUT, accessor) {};
    }

    @Test
    public void testGetA() {
        assertEquals(Long.valueOf(7), instruction.a());
    }

    @Test
    public void testSetA() {
        instruction.a(7L);

        verify(accessor).poke(eq(Long.valueOf(7)));
    }

    @Test
    public void testToString() {
        assertEquals("INPUT 7 (<-- 10)", instruction.toString());
    }
}