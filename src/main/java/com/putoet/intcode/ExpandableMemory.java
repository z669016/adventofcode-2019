package com.putoet.intcode;

import java.util.List;

public class ExpandableMemory implements Memory {
    private long[] memory;

    public ExpandableMemory(List<Integer> memory) {
        if ((memory == null) || memory.size() == 0) throw new Memory.InvalidMemoryInitializer(memory);

        this.memory = copy(memory);
    }

    private static long[] copy(List<Integer> memory) {
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

    protected void checkAddress(Address address) {
        if (address.intValue() >= size()) {
            System.out.println("Increasing memory to include address " + address);
            memory = extend(memory, address.intValue() + 10);
        }
    }
}
