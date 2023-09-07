package com.putoet.intcode;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExpandableMemory implements Memory {
    private long[] memory;

    public ExpandableMemory(@NotNull List<Long> memory) {
        if (memory.isEmpty()) throw new Memory.InvalidMemoryInitializer(memory);

        this.memory = copy(memory);
    }

    private static long[] copy(List<Long> memory) {
        final long[] array = new long[memory.size()];
        for (int idx = 0; idx < memory.size(); idx++)
            array[idx] = memory.get(idx);
        return array;
    }

    private static long[] extend(long[] memory, int additionalSize) {
        final long[] array = new long[memory.length + additionalSize];
        System.arraycopy(memory, 0, array, 0, memory.length);
        return array;
    }

    public long peek(Address address) {
        checkAddress(address);
        return memory[address.intValue()];
    }

    public void poke(Address address, long value) {
        checkAddress(address);
        memory[address.intValue()] = value;
    }

    @Override
    public int size() {
        return memory.length;
    }

    @Override
    public long[] toArray() {
        final long[] array = new long[memory.length];
        System.arraycopy(memory, 0, array, 0, memory.length);

        return array;
    }

    @Override
    public List<Long> toList() {
        return Arrays.stream(memory).boxed().collect(Collectors.toList());
    }

    @Override
    public Memory copy() {
        return new ExpandableMemory(toList());
    }

    protected void checkAddress(Address address) {
        if (address.intValue() >= size()) {
            memory = extend(memory, address.intValue() + 10);
        }
    }
}
