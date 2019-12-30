package com.putoet.day9;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MemoryAccessorFactoryTest {
    @Test
    public void testPositionMemoryAccessor() {
        final Registers regs = new Registers();
        final IMemory memory = Memory.ofIntegerList(List.of(1, 5, 6, 7, 99, 2, 4, 8));
        final IMemoryAccessor accessorA = MemoryAccessorFactory.of(regs, memory, 1);
        final IMemoryAccessor accessorB = MemoryAccessorFactory.of(regs, memory, 2);
        final IMemoryAccessor accessorC = MemoryAccessorFactory.of(regs, memory, 3);

        assertTrue(accessorA instanceof PositionMemoryAccessor);
        assertTrue(accessorB instanceof PositionMemoryAccessor);
        assertTrue(accessorC instanceof PositionMemoryAccessor);
    }

    @Test
    public void testMixedMemoryAccessor() {
        final Registers regs = new Registers().withIncreasedRelativeBase(1);
        final IMemory memory = Memory.ofIntegerList(List.of(2101, 5, 6, 5, 99, 2, 4, 8));
        final ImmediateMemoryAccessor accessorA = (ImmediateMemoryAccessor) MemoryAccessorFactory.of(regs, memory, 1);
        final RelativeBaseMemoryAccessor accessorB = (RelativeBaseMemoryAccessor) MemoryAccessorFactory.of(regs, memory, 2);
        final PositionMemoryAccessor accessorC = (PositionMemoryAccessor) MemoryAccessorFactory.of(regs, memory, 3);

        assertEquals(Address.of(1), accessorA.address());
        assertEquals(Long.valueOf(5), accessorA.peek());

        assertEquals(Address.of(2), accessorB.offsetAddress());
        assertEquals(Address.of(1), accessorB.relativeBaseAddress());
        assertEquals(Address.of(7), accessorB.peekAddress());
        assertEquals(Long.valueOf(8), accessorB.peek());

        assertEquals(Address.of(3), accessorC.address());
        assertEquals(Address.of(5), accessorC.peekAddress());
        assertEquals(Long.valueOf(2), accessorC.peek());
    }
}