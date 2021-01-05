package com.putoet.day5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memory implements IDump, IMemory {
    private final List<Integer> memory;

    private Memory(List<Integer> memory) {
        this.memory = new ArrayList<>();
        this.memory.addAll(memory);
    }

    public static Memory of(List<Integer> memory) {
        if ((memory == null) || memory.size() == 0) throw new IllegalArgumentException("No memory");
        return new Memory(memory);
    }

    @Override
    public Integer peek(Address address) {
        checkAddress(address);
        return memory.get(address.toInt());
    }

    @Override
    public void poke(Address address, Integer value) {
        if (value == null) throw new IllegalArgumentException("Invalid memory write (null)");

        checkAddress(address);
        memory.set(address.toInt(), value);
    }

    @Override
    public int size() {
        return memory.size();
    }

    @Override
    public List<Integer> dump() {
        return Collections.unmodifiableList(memory);
    }

    private void checkAddress(Address address) {
        if (address.toInt() > memory.size() - 1)
            throw new IllegalArgumentException("Invalid memory address " + address.toInt());
    }
}
